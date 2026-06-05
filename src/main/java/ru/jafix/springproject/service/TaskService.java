package ru.jafix.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.mapper.TaskMapper;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task createTask(CreateTaskDto createTaskDto) {
        Task task = taskMapper.toTask(createTaskDto);
        return taskRepository.save(task);
    }

    @CachePut(key = "#task.id", cacheNames = "tasks")
    public Task editTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("При редактировании задачи надо указать ID");
        }

        if (task.getName() == null || task.getName().isBlank()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }

        return taskRepository.save(task);
    }

    @Cacheable(key = "#id", cacheNames = "tasks")
    public Task findById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    String error = "Задачи с таким id не найдено: " + id;
                    log.error(error);
                    return new IllegalArgumentException(error);
                });
    }

    public List<Task> findAll(UUID ownerId, Integer score) {
        if (ownerId != null) {
            return taskRepository.findByOwnerIdOrderByName(ownerId);
        }
        if (score != null) {
            return taskRepository.findByScoreMoreThanNative(score);
        }
        return taskRepository.findAll();
    }

    @CacheEvict(key = "#id", cacheNames = "tasks")
    public void removeTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void completeLowScored() {
        taskRepository.completeLowScoreTasks();
    }
}
