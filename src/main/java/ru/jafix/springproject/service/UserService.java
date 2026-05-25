package ru.jafix.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.mapper.UserMapper;
import ru.jafix.springproject.model.User;
import ru.jafix.springproject.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(CreateUserDto createUserDto) {
        User userToSave = userMapper.toUser(createUserDto);
        userRepository.save(userToSave);
        return userMapper.toUserDto(userToSave);
    }

    public UserDto editUser(UpdateUserDto updateUserDto) {
        User userToSave = userMapper.toUser(updateUserDto);
        userRepository.save(userToSave);
        return userMapper.toUserDto(userToSave);
    }

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

    public void removeById(UUID id) {
        userRepository.deleteById(id);
    }
}
