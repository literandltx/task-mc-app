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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
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

        log.info(String.format(
                "Project: %s, created for user: %s.",
                requestDto.getName(), user.getUsername()));

        return projectMapper.toDto(projectRepository.save(model));
    }

    @Override
    public List<ProjectRespondDto> findAll(
            final Pageable pageable,
            final User user
    ) {
        List<ProjectRespondDto> list = projectRepository.findAllByUser(pageable, user).stream()
                .map(projectMapper::toDto)
                .toList();

        log.info(String.format(
                "User: %s, find all projects",
                user.getUsername()));

        return list;
    }

    @Override
    public ProjectRespondDto findById(
            final Long id,
            final User user
    ) {
        Project project = projectRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + id));
        ProjectRespondDto dto = projectMapper.toDto(project);

        log.info(String.format(
                "User: %s, find project by id: %s.",
                user.getUsername(), id));

        return dto;
    }

    @Override
    public ProjectRespondDto updateById(
            final Long id,
            final UpdateProjectRequestDto requestDto,
            final User user
    ) {
        if (!projectRepository.existsByIdAndUser(id, user)) {
            log.info(String.format(
                    "User: %s, tried to update project by id: %s, but failed. Cannot find project",
                    user.getUsername(), id));
            throw new EntityNotFoundException("Cannot find project with id: " + id);
        }

        final Project model = projectMapper.toModel(requestDto);
        model.setId(id);
        model.setUser(user);

        final Project saved = projectRepository.save(model);

        log.info(String.format(
                "User: %s, updated project by id: %s, successfully",
                user.getUsername(), id));

        return projectMapper.toDto(saved);
    }

    @Override
    public void deleteById(
            final Long id,
            final User user
    ) {
        Optional<Project> project = projectRepository.findByIdAndUserId(id, user.getId());

        if (project.isEmpty()) {
            log.info(String.format(
                    "User: %s, tried to delete projectId: %s, but project do not exists.",
                    user.getUsername(), id));
            throw new RuntimeException(String.format(
                    "Project with id: %s, do not exists.",
                    id));
        }

        projectRepository.deleteById(id);

        log.info(String.format(
                "User: %s, deleted projectId: %s, successfully",
                user.getUsername(), id));
    }
}
