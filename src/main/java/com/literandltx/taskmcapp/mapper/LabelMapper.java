package com.literandltx.taskmcapp.mapper;

import com.literandltx.taskmcapp.config.MapperConfig;
import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.model.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    LabelResponseDto toDto(Label label);

    Label toModel(CreateLabelRequestDto requestDto);

    Label toModel(UpdateLabelRequestDto requestDto);
}
