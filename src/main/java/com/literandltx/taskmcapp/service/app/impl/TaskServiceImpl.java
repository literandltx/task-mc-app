package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.exception.custom.PermissionDeniedException;
import com.literandltx.taskmcapp.mapper.TaskMapper;
import com.literandltx.taskmcapp.model.Attachment;
import com.literandltx.taskmcapp.model.Label;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.Task;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.AttachmentRepository;
import com.literandltx.taskmcapp.repository.LabelRepository;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import com.literandltx.taskmcapp.repository.TaskRepository;
import com.literandltx.taskmcapp.service.app.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final AttachmentRepository attachmentRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDto save(
            final CreateTaskRequestDto requestDto,
            final Long projectId,
            final User user
    ) {
        final Project project = getProjectAndCheckPermission(projectId, user);

        final Task model = taskMapper.toModel(requestDto);
        model.setProject(project);

        final TaskResponseDto dto = taskMapper.toDto(taskRepository.save(model), new HashSet<>());

        log.info(String.format(
                "User: %s, saved taskId: %s, in projectId: %s",
                user.getUsername(), dto.getId(), projectId));

        return dto;
    }

    @Override
    public List<TaskResponseDto> findAll(
            final Long projectId,
            final User user,
            final Pageable pageable
    ) {
        getProjectAndCheckPermission(projectId, user);

        final List<TaskResponseDto> list = taskRepository
                .findAllByProjectId(pageable, projectId).stream()
                .map(task -> {
                    task.setLabels(getTaskLabels(task));
                    return taskMapper.toDto(task, getAttachedFiles(task));
                })
                .toList();

        log.info(String.format(
                "User: %s, found all tasks in projectId: %s",
                user.getUsername(), projectId));

        return list;
    }

    @Override
    public TaskResponseDto findById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        final Task task = taskRepository.findByIdAndProjectId(id, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find task with id: " + id));

        task.setLabels(getTaskLabels(task));
        final TaskResponseDto dto = taskMapper.toDto(task, getAttachedFiles(task));

        log.info(String.format(
                "User: %s, found taskId: %s, in projectId: %s",
                user.getUsername(), id, projectId));

        return dto;
    }

    @Override
    public TaskResponseDto updateById(
            final UpdateTaskRequestDto requestDto,
            final Long id,
            final Long projectId,
            final User user
    ) {
        final Project project = getProjectAndCheckPermission(projectId, user);

        if (!taskRepository.existsByIdAndProjectId(id, projectId)) {
            log.info(String.format(
                    "User: %s, cannot delete: %s, in projectId: %s, taskId do not exists.",
                    user.getUsername(), id, projectId));
            throw new RuntimeException(String.format(
                    "User: %s, cannot delete: %s, in projectId: %s, taskId do not exists.",
                    user.getUsername(), id, projectId));
        }

        final Task model = taskMapper.toModel(requestDto);
        model.setId(id);
        model.setProject(project);

        final Task save = taskRepository.save(model);
        final TaskResponseDto dto = taskMapper.toDto(save, getAttachedFiles(save));

        log.info(String.format(
                "User: %s, updated taskId: %s, in projectId: %s",
                user.getUsername(), id, projectId));

        return dto;
    }

    @Override
    public void deleteById(
            final Long id,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        if (!taskRepository.existsByIdAndProjectId(id, projectId)) {
            log.info(String.format(
                    "User: %s, cannot delete: %s, in projectId: %s, taskId do not exists.",
                    user.getUsername(), id, projectId));
            throw new RuntimeException(String.format(
                    "User: %s, cannot delete: %s, in projectId: %s, taskId do not exists.",
                    user.getUsername(), id, projectId));
        }

        taskRepository.deleteById(id);

        log.info(String.format(
                "User: %s, deleted taskId: %s, from projectId: %s",
                user.getUsername(), id, projectId));
    }

    @Transactional
    @Override
    public void assignLabel(
            final Long labelId,
            final Long taskId,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        if (!taskRepository.existsByIdAndProjectId(taskId, projectId)) {
            log.info(String.format(
                    "User: %s, do not have taskId: %s, in projectId: %s",
                    user.getUsername(), taskId, projectId));
            throw new RuntimeException(String.format(
                    "User: %s, do not have taskId: %s, in projectId: %s",
                    user.getUsername(), taskId, projectId));
        }
        if (!labelRepository.existsByIdAndProjectId(labelId, projectId)) {
            log.info(String.format(
                    "User: %s, do not have labelId: %s, in projectId: %s",
                    user.getUsername(), labelId, projectId));
            throw new RuntimeException(String.format(
                    "User: %s, do not have labelId: %s, in projectId: %s",
                    user.getUsername(), labelId, projectId));
        }

        taskRepository.assignLabelToTask(taskId, labelId);

        log.info(String.format(
                "User: %s, assigned labelId: %s, to taskId: %s",
                user.getUsername(), labelId, taskId));
    }

    @Override
    public void removeLabel(
            final Long labelId,
            final Long taskId,
            final Long projectId,
            final User user
    ) {
        getProjectAndCheckPermission(projectId, user);

        taskRepository.removeLabelFromTask(taskId, labelId);

        log.info(String.format(
                "User: %s, removed labelId: %s, from taskId: %s",
                user.getUsername(), labelId, taskId));
    }

    private Set<Label> getTaskLabels(
            final Task task
    ) {
        return taskRepository.findAllAssignedLabels(task.getId()).stream()
                .map(s -> {
                    final String[] parts = s.split(",");
                    final Label label = new Label();
                    label.setId(Long.valueOf(parts[0]));
                    label.setName(parts[1]);
                    label.setColor(parts[2]);

                    return label;
                })
                .collect(Collectors.toSet());
    }

    private Set<String> getAttachedFiles(
            final Task task
    ) {
        return attachmentRepository.findAllByTaskId(task.getId()).stream()
                .map(Attachment::getFilename)
                .collect(Collectors.toSet());
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
