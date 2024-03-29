package com.literandltx.taskmcapp.dto.project;

import com.literandltx.taskmcapp.model.Project;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectRespondDto {
    private Long id;
    private String name;
    private String description;
    private Project.Status status;
    private LocalDate startDate;
    private LocalDate endDate;
}
