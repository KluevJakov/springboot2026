package ru.jafix.springproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер Пользователей", description = "API для работы с пользователями")
public interface UserController {

    UserDto createUser(CreateUserDto createUserDto);

    UserDto editUser(UpdateUserDto updateUserDto);

    UserDto findById(UUID id);

    List<UserDto> findAll();

    void removeUser(UUID id);
}
