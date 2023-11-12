package com.literandltx.taskmcapp.dto.project;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectRequestDto {
    @NotBlank
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
