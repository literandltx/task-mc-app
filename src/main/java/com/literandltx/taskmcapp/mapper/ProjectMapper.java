package com.literandltx.taskmcapp.mapper;

import com.literandltx.taskmcapp.config.MapperConfig;
import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.model.Project;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    Project toModel(final UpdateProjectRequestDto requestDto);

    Project toModel(final CreateProjectRequestDto requestDto);

    ProjectRespondDto toDto(final Project project);
}
