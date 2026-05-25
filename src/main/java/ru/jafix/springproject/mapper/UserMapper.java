package ru.jafix.springproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.model.User;

import java.util.UUID;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toUser(CreateUserDto createUserDto);

    @Mapping(target = "tasks", ignore = true)
    User toUser(UpdateUserDto updateUserDto);

    UserDto toUserDto(User user);
}
