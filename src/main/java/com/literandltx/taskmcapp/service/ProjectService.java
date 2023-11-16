package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectRespondDto save(CreateProjectRequestDto requestDto, User user);

    List<ProjectRespondDto> findAll(Pageable pageable, User user);

    ProjectRespondDto findById(Long id, User user);

    ProjectRespondDto updateById(Long id, UpdateProjectRequestDto requestDto, User user);

    void deleteById(Long id, User user);
}
