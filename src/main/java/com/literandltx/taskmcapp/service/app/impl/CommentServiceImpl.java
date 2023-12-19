package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.comment.CommentResponseDto;
import com.literandltx.taskmcapp.dto.comment.CreateCommentRequestDto;
import com.literandltx.taskmcapp.exception.custom.PermissionDeniedException;
import com.literandltx.taskmcapp.mapper.CommentMapper;
import com.literandltx.taskmcapp.model.Comment;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.Task;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.CommentRepository;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import com.literandltx.taskmcapp.repository.TaskRepository;
import com.literandltx.taskmcapp.service.app.CommentService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto createComment(
            final CreateCommentRequestDto requestDto,
            final User user,
            final Long projectId,
            final Long taskId
    ) {
        if (checkPermission(user, projectId)) {
            log.info(String.format(
                    "User: %s, have no access to projectId: %s",
                    user.getUsername(), projectId));
            throw new PermissionDeniedException("User have no access to projectId: " + projectId);
        }

        final Task task = taskRepository.findByIdAndProjectId(taskId, projectId).orElseThrow(() ->
                new EntityNotFoundException("Cannot find task in project with id: " + projectId));

        final Comment model = commentMapper.toModel(requestDto);
        model.setTimestamp(LocalDateTime.now());
        model.setTask(task);
        model.setUser(user);

        final Comment save = commentRepository.save(model);

        log.info(String.format(
                "User: %s, create commentId: %s, to taskId: %s",
                user.getUsername(), model.getId(), taskId));

        return commentMapper.toDto(save);
    }

    @Override
    public List<CommentResponseDto> findAllByTask(
            final Pageable pageable,
            final User user,
            final Long projectId,
            final Long taskId
    ) {
        if (checkPermission(user, projectId)) {
            log.info(String.format(
                    "User: %s, have no access to projectId: %s",
                    user.getUsername(), projectId));
            throw new PermissionDeniedException(String.format(
                    "User: %s, have no access to projectId: %s",
                    user.getUsername(), projectId));
        }

        final List<CommentResponseDto> list = commentRepository
                .findAllByUserAndTaskId(pageable, user, taskId).stream()
                .map(commentMapper::toDto)
                .toList();

        log.info(String.format(
                "User: %s, find all comments by projectId: %s, taskId: %s",
                user.getUsername(), projectId, taskId));

        return list;
    }

    private Boolean checkPermission(User user, Long projectId) {
        final Optional<Project> project = projectRepository.findById(projectId);

        if (project.isEmpty()) {
            log.info(String.format(
                    "User: %s, have not projectId: %s.",
                    user.getUsername(), projectId));
            throw new EntityNotFoundException(String.format(
                    "User: %s, have not projectId: %s.",
                    user.getUsername(), projectId));
        }

        return !Objects.equals(project.get().getUser().getId(), user.getId());
    }
}
