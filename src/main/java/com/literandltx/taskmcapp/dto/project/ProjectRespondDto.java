package com.literandltx.taskmcapp.dto.project;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectRespondDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
