package ru.jafix.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.jafix.springproject.config.Constants;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.mapper.UserMapper;
import ru.jafix.springproject.model.Role;
import ru.jafix.springproject.model.User;
import ru.jafix.springproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(CreateUserDto createUserDto) {

        Optional<User> userOptional = userRepository.findByLogin(createUserDto.getLogin());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован");
        }

        User userToSave = userMapper.toUser(createUserDto);

        Role simpleUserRole = new Role();
        simpleUserRole.setId(Constants.Roles.USER_ID);
        userToSave.setRole(simpleUserRole);

        userToSave.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        userRepository.save(userToSave);
        return userMapper.toUserDto(userToSave);
    }

    @CachePut(key = "#updateUserDto.id", cacheNames = "users")
    public UserDto editUser(UpdateUserDto updateUserDto) {
        User userToSave = userMapper.toUser(updateUserDto);

        //TODO: добавить обновление роли пользователя

        userRepository.save(userToSave);
        return userMapper.toUserDto(userToSave);
    }

    @Cacheable(key = "#id", cacheNames = "users")
    public UserDto findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String error = "Пользователя с таким id не найдено: " + id;
                    log.error(error);
                    return new IllegalArgumentException(error);
                });
        return userMapper.toUserDto(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @CacheEvict(key = "#id", cacheNames = "users")
    public void removeById(UUID id) {
        userRepository.deleteById(id);
    }
}
