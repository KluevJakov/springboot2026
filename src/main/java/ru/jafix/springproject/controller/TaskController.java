package ru.jafix.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.dto.common.ErrorDto;
import ru.jafix.springproject.model.Task;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер Задач", description = "API для работы с задачами")
public interface TaskController {

    @Operation(
            summary = "Создание задачи",
            description = "Создание задачи по ее названию и баллам, которые можно получить за ее выполнение"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Задача создана"),
            @ApiResponse(responseCode = "400",
                    description = "Некорректные входные параметры",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            ,
        }
    )
    Task createTask(@Parameter(description = "Модель для создания задачи") CreateTaskDto createTaskDto);

    Task editTask(Task task);

    Task findById(UUID id);

    List<Task> findAll();

    void removeTask(UUID id);
}
