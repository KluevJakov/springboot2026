package ru.jafix.springproject.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.springproject.controller.UserController;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }

    @Override
    @PutMapping
    public UserDto editUser(UpdateUserDto updateUserDto) {
        return userService.editUser(updateUserDto);
    }

    @Override
    @GetMapping("/{id}")
    public UserDto findById(UUID id) {
        return userService.findById(id);
    }

    @Override
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void removeUser(UUID id) {
        userService.removeById(id);
    }

}
