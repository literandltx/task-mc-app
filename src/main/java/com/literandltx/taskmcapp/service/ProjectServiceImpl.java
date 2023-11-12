package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.project.ProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.mapper.ProjectMapper;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectRespondDto save(ProjectRequestDto requestDto) {
        Project model = projectMapper.toModel(requestDto);

        return projectMapper.toDto(projectRepository.save(model));
    }

    @Override
    public List<ProjectRespondDto> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectRespondDto findById(Long id) {
        return projectMapper.toDto(projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find project with id: " + id)));
    }

    @Override
    public ProjectRespondDto updateById(Long id, ProjectRequestDto requestDto) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Cannot find project with id: " + id);
        }

        Project model = projectMapper.toModel(requestDto);
        model.setId(id);

        Project saved = projectRepository.save(model);
        return projectMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
