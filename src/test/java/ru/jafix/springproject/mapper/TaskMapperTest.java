package ru.jafix.springproject.mapper;

import org.junit.jupiter.api.Test;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.model.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskMapperTest {

    private static final UUID USER_UUID = UUID.fromString("96492d59-0ad5-4c00-892d-590ad5ac00f3");
    private static final String TASK_NAME = "Сделать зарядку";
    private static final Integer TASK_SCORE = 23;


    private final TaskMapper taskMapper = new TaskMapperImpl();

    @Test
    void when_correctInput_should_mapCorrect() {
        // assign
        Task expected = Task.builder()
                .id(null)
                .owner(User.builder()
                        .id(USER_UUID)
                        .build())
                .name(TASK_NAME)
                .score(TASK_SCORE)
                .completed(true)
                .build();

        CreateTaskDto input = CreateTaskDto.builder()
                .name(TASK_NAME)
                .ownerId(USER_UUID)
                .score(TASK_SCORE)
                .build();

        // act
        Task actual = taskMapper.toTask(input);

        // assert
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getScore(), actual.getScore());
        assertNotNull(actual.getOwner());
        assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
    }

    @Test
    void when_nullInput_should_throwException() {
        // act
        Task actual = taskMapper.toTask(null);

        // assert
        assertNull(actual);
    }

}

