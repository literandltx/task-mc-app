package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
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
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            CreateTaskRequestDto requestDto,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }
        Task model = taskMapper.toModel(requestDto);
        model.setProject(project);

        Task saved = taskRepository.save(model);
        return taskMapper.toDto(saved, new HashSet<>());
    }

    @Override
    public List<TaskResponseDto> findAll(
            Long projectId,
            User user,
            Pageable pageable
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User have not project with id: " + projectId);
        }

        return taskRepository.findAllByProjectId(pageable, projectId).stream()
                .map(task -> {
                    task.setLabels(getTaskLabels(task));
                    return taskMapper.toDto(task, getAttachedFiles(task));
                })
                .toList();
    }

    @Override
    public TaskResponseDto findById(
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));

        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User does not have project with id: " + projectId);
        }

        Task task = taskRepository.findByIdAndProjectId(id, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find task with id: " + id));

        task.setLabels(getTaskLabels(task));
        return taskMapper.toDto(task, getAttachedFiles(task));
    }

    @Override
    public TaskResponseDto updateById(
            UpdateTaskRequestDto requestDto,
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User have not project with id: " + projectId);
        }

        if (!taskRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot update task with id: " + id);
        }

        Task model = taskMapper.toModel(requestDto);
        model.setId(id);
        model.setProject(project);

        return taskMapper.toDto(taskRepository.save(model), getAttachedFiles(model));
    }

    @Override
    public void deleteById(
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User have not project with id: " + projectId);
        }

        if (!taskRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot delete task with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void assignLabel(
            Long labelId,
            Long taskId,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User have not project with id: " + projectId);
        }

        if (!taskRepository.existsByIdAndProjectId(taskId, projectId)) {
            throw new RuntimeException("Cannot find task with id: " + taskId);
        }
        if (!labelRepository.existsByIdAndProjectId(labelId, projectId)) {
            throw new RuntimeException("Cannot find label with id");
        }

        taskRepository.assignLabelToTask(taskId, labelId);
    }

    @Override
    public void removeLabel(
            Long labelId,
            Long taskId,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User have not project with id: " + projectId);
        }

        taskRepository.removeLabelFromTask(taskId, labelId);
    }

    private Set<Label> getTaskLabels(Task task) {
        return taskRepository.findAllAssignedLabels(task.getId()).stream()
                .map(s -> {
                    String[] parts = s.split(",");
                    Label label = new Label();
                    label.setId(Long.valueOf(parts[0]));
                    label.setName(parts[1]);
                    label.setColor(parts[2]);

                    return label;
                })
                .collect(Collectors.toSet());
    }

    private Set<String> getAttachedFiles(Task task) {
        return attachmentRepository.findAllByTaskId(task.getId()).stream()
                .map(Attachment::getFilename)
                .collect(Collectors.toSet());
    }
}
