package com.literandltx.taskmcapp.mapper;

import com.literandltx.taskmcapp.config.MapperConfig;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.model.Task;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    Task toModel(final CreateTaskRequestDto requestDto);

    Task toModel(final UpdateTaskRequestDto requestDto);

    @Mappings({
            @Mapping(source = "task.project.id", target = "projectId"),
            @Mapping(target = "labelIds", ignore = true),
            @Mapping(source = "attachedFiles", target = "attachedFiles")
    })
    TaskResponseDto toDto(final Task task, final Set<String> attachedFiles);

    @AfterMapping
    default void setTaskLabels(@MappingTarget final TaskResponseDto responseDto, final Task task) {
        final Set<LabelResponseDto> collect = task.getLabels().stream()
                .map(s -> {
                    final LabelResponseDto dto = new LabelResponseDto();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setColor(s.getColor());

                    return dto;
                })
                .collect(Collectors.toSet());

        responseDto.setLabelIds(collect);
    }
}
