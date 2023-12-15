package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskResponseDto save(
            final CreateTaskRequestDto requestDto,
            final Long projectId,
            final User user
    );

    List<TaskResponseDto> findAll(
            final Long projectId,
            final User user,
            final Pageable pageable
    );

    TaskResponseDto findById(
            final Long id,
            final Long projectId,
            final User user
    );

    TaskResponseDto updateById(
            final UpdateTaskRequestDto requestDto,
            final Long id,
            final Long projectId,
            final User user
    );

    void deleteById(
            final Long id,
            final Long projectId,
            final User user
    );

    void assignLabel(
            Long labelId,
            Long taskId,
            Long projectId,
            User user
    );

    void removeLabel(
            Long labelId,
            Long taskId,
            Long projectId,
            User user
    );
}
