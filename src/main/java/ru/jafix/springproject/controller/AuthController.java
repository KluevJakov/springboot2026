package ru.jafix.springproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
