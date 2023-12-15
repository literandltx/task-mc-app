package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectRespondDto save(
            final CreateProjectRequestDto requestDto,
            final User user
    );

    List<ProjectRespondDto> findAll(
            final Pageable pageable,
            final User user
    );

    ProjectRespondDto findById(
            final Long id,
            final User user
    );

    ProjectRespondDto updateById(
            final Long id,
            final UpdateProjectRequestDto requestDto,
            final User user
    );

    void deleteById(
            final Long id,
            final User user
    );
}
