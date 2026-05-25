package ru.jafix.springproject.controller.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jafix.springproject.dto.common.ErrorDto;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorDto errorDto = ErrorDto.builder()
                .errors(errorMessages)
                .build();

        log.error("Ошибка валидации: {}", errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(exception = ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build();

        log.error("Ошибка валидации: {}", errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error("Ошибка: {}", e.getMessage());

        return ResponseEntity.internalServerError().body(ErrorDto.builder()
                .errors(List.of(e.getMessage()))
                .build());
    }

}
