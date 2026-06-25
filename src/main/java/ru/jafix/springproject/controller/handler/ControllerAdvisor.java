package ru.jafix.springproject.controller.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jafix.springproject.dto.common.ErrorDto;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {

    public static final String VALIDATION_ERROR_MSG = "Ошибка валидации: {}";

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorDto errorDto = ErrorDto.builder()
                .errors(errorMessages)
                .build();

        log.error(VALIDATION_ERROR_MSG, errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(exception = ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build();

        log.error(VALIDATION_ERROR_MSG, errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(exception = IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build();

        log.error(VALIDATION_ERROR_MSG, errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(exception = AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(errorDto);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error("Ошибка: {}", e.getMessage());

        return ResponseEntity.internalServerError().body(ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build());
    }

}
