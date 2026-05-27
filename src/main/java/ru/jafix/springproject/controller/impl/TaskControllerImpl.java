package ru.jafix.springproject.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.springproject.controller.TaskController;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Task createTask(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.createTask(createTaskDto);
    }

    @Override
    @PutMapping
    public Task editTask(@RequestBody Task task) {
        return taskService.editTask(task);
    }

    @Override
    @PatchMapping
    public void completeTask() {
        taskService.completeLowScored();
    }

    @Override
    @GetMapping("/{id}")
    public Task findById(@PathVariable("id") UUID id) {
        return taskService.findById(id);
    }

    @Override
    @GetMapping
    public List<Task> findAll(@RequestParam(value = "ownerId", required = false) UUID ownerId,
                              @RequestParam(value = "score", required = false) Integer score) {
        return taskService.findAll(ownerId, score);
    }

    @Override
    @DeleteMapping("/{id}")
    public void removeTask(@PathVariable("id") UUID id) {
        taskService.removeTask(id);
    }

}
