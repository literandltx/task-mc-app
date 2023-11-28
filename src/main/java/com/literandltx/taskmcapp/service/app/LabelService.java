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
            CreateLabelRequestDto requestDto,
            Long projectId,
            User user
    );

    List<LabelResponseDto> findAll(
            Long projectId,
            User user,
            Pageable pageable
    );

    LabelResponseDto findById(
             Long id,
             Long projectId,
             User user
    );

    LabelResponseDto updateById(
            UpdateLabelRequestDto requestDto,
            Long id,
            Long projectId,
            User user
    );

    void deleteById(
            Long id,
            Long projectId,
            User user
    );
}
