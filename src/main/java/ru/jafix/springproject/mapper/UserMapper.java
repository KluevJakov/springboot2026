package ru.jafix.springproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafix.springproject.dto.users.CreateUserDto;
import ru.jafix.springproject.dto.users.UpdateUserDto;
import ru.jafix.springproject.dto.users.UserDto;
import ru.jafix.springproject.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(CreateUserDto createUserDto);

    User toUser(UpdateUserDto updateUserDto);

    UserDto toUserDto(User user);
}
