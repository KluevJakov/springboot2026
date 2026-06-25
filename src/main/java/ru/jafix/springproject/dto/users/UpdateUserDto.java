package ru.jafix.springproject.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class UpdateUserDto {
    @NotBlank
    private UUID id;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
