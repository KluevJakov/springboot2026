package ru.jafix.springproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.dto.tasks.TaskDto;
import ru.jafix.springproject.model.Task;
import ru.jafix.springproject.model.User;

import java.util.UUID;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "owner", expression = "java(toOwner(taskDto.getOwnerId()))")
    @Mapping(target = "completed", ignore = true)
    Task toTask(CreateTaskDto taskDto);

    Task fromDto(TaskDto dto);

    default User toOwner(UUID ownerId) {
        User owner = new User();
        owner.setId(ownerId);
        return owner;
    }
}
