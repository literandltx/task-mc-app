package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.task.CreateTaskRequestDto;
import com.literandltx.taskmcapp.dto.task.TaskResponseDto;
import com.literandltx.taskmcapp.dto.task.UpdateTaskRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.TaskService;
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

@RequiredArgsConstructor
@RequestMapping("/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponseDto createNewTask(
            @RequestBody @Valid CreateTaskRequestDto requestDto,
            Authentication authentication,
            @RequestParam Long projectId
    ) {
        User user = (User) authentication.getPrincipal();

        return taskService.save(requestDto, projectId, user);
    }

    @GetMapping
    public List<TaskResponseDto> retrieveTasksProjects(
            Pageable pageable,
            Authentication authentication,
            @RequestParam Long projectId
    ) {
        User user = (User) authentication.getPrincipal();

        return taskService.findAll(projectId, user, pageable);
    }

    @GetMapping("/{id}")
    public TaskResponseDto retrieveTaskDetails(
            Authentication authentication,
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        User user = (User) authentication.getPrincipal();

        return taskService.findById(id, projectId, user);
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(
            @RequestBody @Valid UpdateTaskRequestDto requestDto,
            Authentication authentication,
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        User user = (User) authentication.getPrincipal();

        return taskService.updateById(requestDto, id, projectId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(
            Authentication authentication,
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        User user = (User) authentication.getPrincipal();

        taskService.deleteById(id, projectId, user);
    }

    @PatchMapping("/assign")
    public void assignLabel(
            @RequestParam(name = "labelId") Long labelId,
            @RequestParam(name = "taskId") Long taskId,
            @RequestParam(name = "projectId") Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        taskService.assignLabel(labelId, taskId, projectId, user);
    }

    @PatchMapping("/remove")
    public void removeLabel(
            @RequestParam(name = "labelId") Long labelId,
            @RequestParam(name = "taskId") Long taskId,
            @RequestParam(name = "projectId") Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        taskService.removeLabel(labelId, taskId, projectId, user);
    }
}
