package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LabelService {
    LabelResponseDto create(
            final CreateLabelRequestDto requestDto,
            final Long projectId,
            final User user
    );

    List<LabelResponseDto> findAll(
            final Long projectId,
            final User user,
            final Pageable pageable
    );

    LabelResponseDto findById(
            final Long id,
            final Long projectId,
            final User user
    );

    LabelResponseDto updateById(
            final UpdateLabelRequestDto requestDto,
            final Long id,
            final Long projectId,
            final User user
    );

    void deleteById(
            final Long id,
            final Long projectId,
            final User user
    );
}
