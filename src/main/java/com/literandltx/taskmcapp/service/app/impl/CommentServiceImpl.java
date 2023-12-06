package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.comment.CommentResponseDto;
import com.literandltx.taskmcapp.dto.comment.CreateCommentRequestDto;
import com.literandltx.taskmcapp.mapper.CommentMapper;
import com.literandltx.taskmcapp.model.Comment;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.Task;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.CommentRepository;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import com.literandltx.taskmcapp.repository.TaskRepository;
import com.literandltx.taskmcapp.service.app.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponseDto createComment(
            CreateCommentRequestDto requestDto,
            User user,
            Long projectId,
            Long taskId
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find task in project:"));

        Comment model = commentMapper.toModel(requestDto);
        model.setTimestamp(LocalDateTime.now());
        model.setTask(task);
        model.setUser(user);

        Comment saved = commentRepository.save(model);

        return commentMapper.toDto(saved);
    }

    @Override
    public List<CommentResponseDto> findAllByTask(
            Pageable pageable,
            User user,
            Long projectId,
            Long taskId
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find task in project:"));

        return commentRepository.findAllByTaskId(pageable, task.getId()).stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
