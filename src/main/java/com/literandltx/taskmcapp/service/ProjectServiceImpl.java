package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.mapper.ProjectMapper;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.User;
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
    public ProjectRespondDto save(
            CreateProjectRequestDto requestDto,
            User user
    ) {
        Project model = projectMapper.toModel(requestDto);
        model.setUser(user);
        model.setStatus(Project.Status.INITIATED);

        return projectMapper.toDto(projectRepository.save(model));
    }

    @Override
    public List<ProjectRespondDto> findAll(
            Pageable pageable,
            User user
    ) {
        return projectRepository.findAllByUser(pageable, user).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectRespondDto findById(
            Long id,
            User user
    ) {
        return projectMapper.toDto(projectRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Cannot find project with id: " + id)));
    }

    @Override
    public ProjectRespondDto updateById(
            Long id,
            UpdateProjectRequestDto requestDto,
            User user
    ) {
        if (!projectRepository.existsByIdAndUser(id, user)) {
            throw new RuntimeException("Cannot find project with id: " + id);
        }

        Project model = projectMapper.toModel(requestDto);
        model.setId(id);
        model.setUser(user);

        Project saved = projectRepository.save(model);
        return projectMapper.toDto(saved);
    }

    @Override
    public void deleteById(
            Long id,
            User user
    ) {
        projectRepository.deleteById(id);
    }
}
