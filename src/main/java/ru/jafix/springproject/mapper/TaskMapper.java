package ru.jafix.springproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.jafix.springproject.dto.tasks.CreateTaskDto;
import ru.jafix.springproject.model.Task;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "completed", ignore = true)
    Task toTask(CreateTaskDto taskDto);
}
