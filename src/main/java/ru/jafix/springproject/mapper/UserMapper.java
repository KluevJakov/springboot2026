package ru.jafix.springproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "additionalInfo", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(CreateUserDto createUserDto);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "additionalInfo", ignore = true)
    User toUser(UpdateUserDto updateUserDto);

    @Mapping(target = "tasks", ignore = true)
    UserDto toUserDto(User user);
}
