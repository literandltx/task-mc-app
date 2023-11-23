package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskResponseDto save(CreateTaskRequestDto requestDto, Long projectId, User user);

    List<TaskResponseDto> findAll(Long projectId, User user, Pageable pageable);

    TaskResponseDto findById(Long id, Long projectId, User user);

    TaskResponseDto updateById(UpdateTaskRequestDto requestDto, Long id, Long projectId, User user);

    void deleteById(Long id, Long projectId, User user);

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
