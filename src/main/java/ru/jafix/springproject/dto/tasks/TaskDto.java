package ru.jafix.springproject.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jafix.springproject.dto.users.UserDto;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private String name;
    private boolean completed;
    private Integer score;
    private UserDto owner;
}
