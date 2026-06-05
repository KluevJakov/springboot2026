package ru.jafix.springproject.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jafix.springproject.model.Task;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private UUID id;
    private String login;
    private List<Task> tasks;
}
