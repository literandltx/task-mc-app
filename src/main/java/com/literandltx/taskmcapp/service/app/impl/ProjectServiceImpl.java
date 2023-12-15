package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.mapper.ProjectMapper;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import com.literandltx.taskmcapp.service.app.ProjectService;
import jakarta.persistence.EntityNotFoundException;
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
            final CreateProjectRequestDto requestDto,
            final User user
    ) {
        final Project model = projectMapper.toModel(requestDto);
        model.setUser(user);
        model.setStatus(Project.Status.INITIATED);

        return projectMapper.toDto(projectRepository.save(model));
    }

    @Override
    public List<ProjectRespondDto> findAll(
            final Pageable pageable,
            final User user
    ) {
        return projectRepository.findAllByUser(pageable, user).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectRespondDto findById(
            final Long id,
            final User user
    ) {
        return projectMapper.toDto(projectRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Cannot find project with id: " + id)));
    }

    @Override
    public ProjectRespondDto updateById(
            final Long id,
            final UpdateProjectRequestDto requestDto,
            final User user
    ) {
        if (!projectRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Cannot find project with id: " + id);
        }

        final Project model = projectMapper.toModel(requestDto);
        model.setId(id);
        model.setUser(user);

        final Project saved = projectRepository.save(model);
        return projectMapper.toDto(saved);
    }

    @Override
    public void deleteById(
            final Long id,
            final User user
    ) {
        projectRepository.deleteById(id);
    }
}
