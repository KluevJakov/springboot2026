package ru.jafix.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.jafix.springproject.config.Constants;
import ru.jafix.springproject.dto.common.Status;
import ru.jafix.springproject.dto.common.StatusDto;
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
    private final MailService mailService;
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

        UUID activateKey = UUID.randomUUID();

        userToSave.setActivateKey(activateKey);

        userRepository.save(userToSave);

        mailService.send("Активация аккаунта",
                "Для активации аккаунта перейдите по ссылке: http://localhost:8080/api/activate/" + activateKey,
                userToSave.getLogin());

        return userMapper.toUserDto(userToSave);
    }

    public StatusDto activate (UUID activateKey) {
        Optional<User> optionalUser = userRepository.findByActivateKey(activateKey);

        if (optionalUser.isEmpty()) {
            return StatusDto.builder()
                    .message("Неверный код активации")
                    .status(Status.ERROR)
                    .build();
        }

        User userToActivate = optionalUser.get();

        userToActivate.setActivateKey(null);
        userToActivate.setEnable(true);

        userRepository.save(userToActivate);

        return StatusDto.builder()
                .status(Status.SUCCESS)
                .message("Аккаунт активирован успешно, можете проходить процедуру аутентификации")
                .build();
    }

    @CachePut(key = "#updateUserDto.id", cacheNames = "users")
    public UserDto editUser(UpdateUserDto updateUserDto) {
        User userToSave = userMapper.toUser(updateUserDto);

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
