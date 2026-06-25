package ru.jafix.springproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jafix.springproject.dto.common.StatusDto;
import ru.jafix.springproject.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activate")
public class ActivateController {

    private final UserService userService;

    @GetMapping("/{activateKey}")
    public StatusDto activate(@PathVariable("activateKey") UUID activateKey) {
        return userService.activate(activateKey);
    }
}
