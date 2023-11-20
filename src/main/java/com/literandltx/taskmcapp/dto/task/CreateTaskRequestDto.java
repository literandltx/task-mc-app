package com.literandltx.taskmcapp.dto.task;

import com.literandltx.taskmcapp.model.Task;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateTaskRequestDto {
    private String name;
    private String description;
    private LocalDate dueDate;
    private Task.Priority priority;
    private Task.Status status;
}
