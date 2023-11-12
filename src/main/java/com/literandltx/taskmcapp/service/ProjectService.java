package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.project.ProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectRespondDto save(ProjectRequestDto requestDto);

    List<ProjectRespondDto> findAll(Pageable pageable);

    ProjectRespondDto findById(Long id);

    ProjectRespondDto updateById(Long id, ProjectRequestDto requestDto);

    void deleteById(Long id);
}
