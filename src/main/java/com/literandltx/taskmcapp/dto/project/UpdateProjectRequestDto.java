package com.literandltx.taskmcapp.dto.project;

import com.literandltx.taskmcapp.model.Project;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UpdateProjectRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Project.Status status;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;
}
