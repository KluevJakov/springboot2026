package ru.jafix.springproject.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }

    @Override
    @PutMapping
    public UserDto editUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.editUser(updateUserDto);
    }

    @Override
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") UUID id) {
        return userService.findById(id);
    }

    @Override
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") UUID id) {
        userService.removeById(id);
    }

}
