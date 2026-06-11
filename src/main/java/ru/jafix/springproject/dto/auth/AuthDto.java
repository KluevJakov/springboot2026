package ru.jafix.springproject.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
