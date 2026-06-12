package ru.jafix.springproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jafix.springproject.dto.auth.AuthDto;
import ru.jafix.springproject.dto.auth.JwtResponse;
import ru.jafix.springproject.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public JwtResponse auth(@RequestBody AuthDto authDto) {
        return authService.auth(authDto);
    }
}
