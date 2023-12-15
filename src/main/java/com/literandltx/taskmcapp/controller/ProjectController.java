package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.project.CreateProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.dto.project.UpdateProjectRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Project manager")
@RequiredArgsConstructor
@RequestMapping("/projects")
@RestController
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Create new project")
    @PostMapping
    public ProjectRespondDto createNewProject(
            final Authentication authentication,
            @RequestBody @Valid final CreateProjectRequestDto requestDto
    ) {
        final User user = (User) authentication.getPrincipal();

        return projectService.save(requestDto, user);
    }

    @Operation(summary = "Retrieve all user's projects")
    @GetMapping
    public List<ProjectRespondDto> retrieveUsersProjects(
            final Authentication authentication,
            final Pageable pageable
    ) {
        final User user = (User) authentication.getPrincipal();

        return projectService.findAll(pageable, user);
    }

    @Operation(summary = "Retrieve user project by id")
    @GetMapping("/{id}")
    public ProjectRespondDto retrieveProjectDetails(
            final Authentication authentication,
            @PathVariable final Long id
    ) {
        final User user = (User) authentication.getPrincipal();

        return projectService.findById(id, user);
    }

    @Operation(summary = "Update project by id")
    @PutMapping("/{id}")
    public ProjectRespondDto updateProject(
            final Authentication authentication,
            @PathVariable final Long id,
            @RequestBody final UpdateProjectRequestDto requestDto
    ) {
        final User user = (User) authentication.getPrincipal();

        return projectService.updateById(id, requestDto, user);
    }

    @Operation(summary = "Delete project by id")
    @DeleteMapping("/{id}")
    public void deleteProject(
            final Authentication authentication,
            @PathVariable final Long id
    ) {
        final User user = (User) authentication.getPrincipal();

        projectService.deleteById(id, user);
    }
}
