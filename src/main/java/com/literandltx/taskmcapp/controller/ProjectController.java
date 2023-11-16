package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/projects")
@RestController
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ProjectRespondDto createNewProject(
            Authentication authentication,
            @RequestBody @Valid CreateProjectRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();

        return projectService.save(requestDto, user);
    }

    @GetMapping
    public List<ProjectRespondDto> retrieveUsersProjects(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();

        return projectService.findAll(pageable, user);
    }

    @GetMapping("/{id}")
    public ProjectRespondDto retrieveProjectDetails(
            Authentication authentication,
            @PathVariable Long id
    ) {
        User user = (User) authentication.getPrincipal();

        return projectService.findById(id, user);
    }

    @PutMapping("/{id}")
    public ProjectRespondDto updateProject(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody UpdateProjectRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();

        return projectService.updateById(id, requestDto, user);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(
            Authentication authentication,
            @PathVariable Long id
    ) {
        User user = (User) authentication.getPrincipal();

        projectService.deleteById(id, user);
    }
}
