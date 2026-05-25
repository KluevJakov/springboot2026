package ru.jafix.springproject.dto.users;

import lombok.Data;
import ru.jafix.springproject.model.Task;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateUserDto {
    private UUID id;
    private String login;
}
