package ru.jafix.springproject.dto.tasks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель для создания задачи")
public class CreateTaskDto {
    @NotBlank(message = "Название задачи не может быть пустым")
    @Schema(description = "Название задачи", example = "Сходить в спортзал")
    private String name;

    @Min(value = 1L, message = "Кол-во очков должно быть не меньше 1")
    @Max(value = 100L, message = "Кол-во очков должно быть не больше 100")
    @Schema(description = "Виртуальные баллы за выполнение задачи", example = "20")
    private Integer score;

    @NotNull(message = "Идентификатор пользоателя")
    @Schema(description = "Идентификатор владельца задачи")
    private UUID ownerId;
}
