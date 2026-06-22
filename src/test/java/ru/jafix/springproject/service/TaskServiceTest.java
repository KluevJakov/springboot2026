package ru.jafix.springproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.mapper.TaskMapper;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.model.User;
import ru.jafix.springproject.repository.TaskRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest extends CommonServiceTest {

    private static final UUID TASK_UUID = UUID.fromString("96492d59-0ad5-4c00-892d-590ad5ac00f1");
    private static final UUID USER_UUID = UUID.fromString("96492d59-0ad5-4c00-892d-590ad5ac00f3");
    private static final String TASK_NAME = "Сделать зарядку";
    private static final Integer TASK_SCORE = 23;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void when_createTaskCorrectArg_should_returnCorrectAnswer() {
        CreateTaskDto input = CreateTaskDto.builder()
                .name(TASK_NAME)
                .ownerId(USER_UUID)
                .score(TASK_SCORE)
                .build();

        Task fromMapper = Task.builder()
                .name(TASK_NAME)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        Task fromDb = Task.builder()
                .id(TASK_UUID)
                .name(TASK_NAME)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        when(taskMapper.toTask(any()))
                .thenReturn(fromMapper);

        when(taskRepository.save(eq(fromMapper)))
                .thenReturn(fromDb);

        Task actual = taskService.createTask(input);

        assertEquals(fromDb, actual);

        verify(taskMapper).toTask(input);
        verify(taskRepository).save(fromMapper);
    }

    @Test
    public void when_editTaskCorrectArg_should_returnCorrectAnswer() {
        Task input = Task.builder()
                .id(TASK_UUID)
                .name(TASK_NAME)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        Task fromDb = Task.builder()
                .id(TASK_UUID)
                .name(TASK_NAME)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        when(taskRepository.save(eq(input)))
                .thenReturn(fromDb);

        Task actual = taskService.editTask(input);

        assertEquals(fromDb, actual);

        verify(taskRepository).save(input);
    }

    @Test
    public void when_editTaskNullId_should_throwException() {
        Task input = Task.builder()
                .name(TASK_NAME)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        assertThrows(IllegalArgumentException.class, () -> taskService.editTask(input));
    }

    @Test
    public void when_editTaskNullName_should_throwException() {
        Task input = Task.builder()
                .id(TASK_UUID)
                .name(null)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        assertThrows(IllegalArgumentException.class, () -> taskService.editTask(input));
    }

    @Test
    public void when_editTaskEmptyName_should_throwException() {
        Task input = Task.builder()
                .id(TASK_UUID)
                .name("")
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .completed(true)
                .score(TASK_SCORE)
                .build();

        assertThrows(IllegalArgumentException.class, () -> taskService.editTask(input));
    }

}
