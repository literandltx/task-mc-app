package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Task manager")
@RequiredArgsConstructor
@RequestMapping("/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Create new task assigned to projectId")
    @PostMapping
    public TaskResponseDto createNewTask(
            @RequestBody @Valid final CreateTaskRequestDto requestDto,
            final Authentication authentication,
            @RequestParam final Long projectId
    ) {
        final User user = (User) authentication.getPrincipal();

        return taskService.save(requestDto, projectId, user);
    }

    @Operation(summary = "Retrieve all tasks information in projectId")
    @GetMapping
    public List<TaskResponseDto> retrieveTasksProjects(
            final Pageable pageable,
            final Authentication authentication,
            @RequestParam final Long projectId
    ) {
        final User user = (User) authentication.getPrincipal();

        return taskService.findAll(projectId, user, pageable);
    }

    @Operation(summary = "Retrieve task information in projectId")
    @GetMapping("/{id}")
    public TaskResponseDto retrieveTaskDetails(
            final Authentication authentication,
            @PathVariable final Long id,
            @RequestParam final Long projectId
    ) {
        final User user = (User) authentication.getPrincipal();

        return taskService.findById(id, projectId, user);
    }

    @Operation(summary = "Update task information in projectId")
    @PutMapping("/{id}")
    public TaskResponseDto updateTask(
            @RequestBody @Valid final UpdateTaskRequestDto requestDto,
            final Authentication authentication,
            @PathVariable final Long id,
            @RequestParam final Long projectId
    ) {
        final User user = (User) authentication.getPrincipal();

        return taskService.updateById(requestDto, id, projectId, user);
    }

    @Operation(summary = "Delete task in projectId")
    @DeleteMapping("/{id}")
    public void deleteTask(
            final Authentication authentication,
            @PathVariable final Long id,
            @RequestParam final Long projectId
    ) {
        final User user = (User) authentication.getPrincipal();

        taskService.deleteById(id, projectId, user);
    }

    @Operation(summary = "Assign label to task in projectId")
    @PatchMapping("/assign")
    public void assignLabel(
            @RequestParam("labelId") final Long labelId,
            @RequestParam("taskId") final Long taskId,
            @RequestParam("projectId") final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        taskService.assignLabel(labelId, taskId, projectId, user);
    }

    @Operation(summary = "Remove label from task in projectId")
    @PatchMapping("/remove")
    public void removeLabel(
            @RequestParam("labelId") final Long labelId,
            @RequestParam("taskId") final Long taskId,
            @RequestParam("projectId") final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        taskService.removeLabel(labelId, taskId, projectId, user);
    }
}
