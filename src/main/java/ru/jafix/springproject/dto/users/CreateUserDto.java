package ru.jafix.springproject.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotBlank
    private String login;
}
