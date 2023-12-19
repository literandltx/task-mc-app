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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
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
        final Project project = getProjectAndCheckPermission(projectId, user);

        final Label model = labelMapper.toModel(requestDto);
        model.setProject(project);

        final Label save = labelRepository.save(model);

        log.info(String.format(
                "User: %s, created labelId: %s, in projectId: %s",
                user.getUsername(), save.getId(), projectId));

        return labelMapper.toDto(save);
    }

    @Override
    public List<LabelResponseDto> findAll(
            final Long projectId,
            final User user,
            final Pageable pageable
    ) {
        getProjectAndCheckPermission(projectId, user);

        final List<LabelResponseDto> list = labelRepository
                .findAllByProjectId(pageable, projectId).stream()
                .map(labelMapper::toDto)
                .toList();

        log.info(String.format(
                "User: %s, find by all labels in projectId: %s",
                user.getUsername(), projectId));

        return list;
    }

    @Override
    public LabelResponseDto findById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        final Optional<Label> label = labelRepository.findByIdAndProjectId(id, projectId);

        if (label.isEmpty()) {
            log.info(String.format(
                    "User: %s, cannot find labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
            throw new RuntimeException(String.format(
                    "User: %s, cannot find labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
        }

        log.info(String.format(
                "User: %s, find by labelId: %s, in projectId: %s",
                user.getUsername(), id, projectId));

        return labelMapper.toDto(label.get());
    }

    @Override
    public LabelResponseDto updateById(
            final UpdateLabelRequestDto requestDto,
            final Long id,
            final Long projectId,
            final User user
    ) {
        final Project project = getProjectAndCheckPermission(projectId, user);

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            log.info(String.format(
                    "User: %s, cannot delete labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
            throw new RuntimeException(String.format(
                    "User: %s, cannot delete labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
        }

        final Label model = labelMapper.toModel(requestDto);
        model.setId(id);
        model.setProject(project);

        final Label save = labelRepository.save(model);

        log.info(String.format(
                "User: %s, updated labelId: %s, in projectId: %s",
                user.getUsername(), id, projectId));

        return labelMapper.toDto(save);
    }

    @Override
    public void deleteById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            log.info(String.format(
                    "User: %s, cannot delete labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
            throw new RuntimeException(String.format(
                    "User: %s, cannot delete labelId: %s, projectId do not have labelId.",
                    user.getUsername(), id));
        }

        labelRepository.deleteById(id);

        log.info(String.format(
                "User: %s, deleted labelId: %s, in projectId: %s",
                user.getUsername(), id, projectId));
    }

    private Project getProjectAndCheckPermission(
            final Long projectId,
            final User user
    ) {
        final Optional<Project> project = projectRepository.findById(projectId);

        if (project.isEmpty()) {
            log.info(String.format(
                    "User: %s, have not projectId: %s.",
                    user.getUsername(), projectId));
            throw new EntityNotFoundException(String.format(
                    "User: %s, have not projectId: %s.",
                    user.getUsername(), projectId));
        }

        if (!Objects.equals(project.get().getUser().getId(), user.getId())) {
            log.info(String.format(
                    "User: %s, have no access to projectId: %s",
                    user.getUsername(), projectId));
            throw new PermissionDeniedException(String.format(
                    "User: %s, have no access to projectId: %s",
                    user.getUsername(), projectId));
        }

        return project.get();
    }
}
