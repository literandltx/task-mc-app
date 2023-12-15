package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.exception.custom.PermissionDeniedException;
import com.literandltx.taskmcapp.mapper.LabelMapper;
import com.literandltx.taskmcapp.model.Label;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.LabelRepository;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import com.literandltx.taskmcapp.service.app.LabelService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;

    @Override
    public LabelResponseDto create(
            final CreateLabelRequestDto requestDto,
            final Long projectId,
            final User user
    ) {
        final Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }
        final Label model = labelMapper.toModel(requestDto);
        model.setProject(project);

        return labelMapper.toDto(labelRepository.save(model));
    }

    @Override
    public List<LabelResponseDto> findAll(
            final Long projectId,
            final User user,
            final Pageable pageable
    ) {
        final Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }

        return labelRepository.findAllByProjectId(pageable, projectId).stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelResponseDto findById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        final Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }

        final Label label = labelRepository.findByIdAndProjectId(id, projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find label with id: " + id));

        return labelMapper.toDto(label);
    }

    @Override
    public LabelResponseDto updateById(
            final UpdateLabelRequestDto requestDto,
            final Long id,
            final Long projectId,
            final User user
    ) {
        final Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot update label with id: " + id);
        }

        final Label model = labelMapper.toModel(requestDto);
        model.setId(id);
        model.setProject(project);

        return labelMapper.toDto(model);
    }

    @Override
    public void deleteById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        final Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot delete label with id: " + id);
        }

        labelRepository.deleteById(id);
    }
}
