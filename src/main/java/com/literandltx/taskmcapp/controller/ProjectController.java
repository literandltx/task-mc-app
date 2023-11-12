package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.project.ProjectRequestDto;
import com.literandltx.taskmcapp.dto.project.ProjectRespondDto;
import com.literandltx.taskmcapp.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ProjectRespondDto createNewProject(@RequestBody @Valid ProjectRequestDto requestDto) {
        return projectService.save(requestDto);
    }

    @GetMapping
    public List<ProjectRespondDto> retrieveUsersProjects(Pageable pageable) {
        return projectService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProjectRespondDto retrieveProjectDetails(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @PutMapping("/{id}")
    public ProjectRespondDto updateProject(@PathVariable Long id, @RequestBody ProjectRequestDto requestDto) {
        return projectService.updateById(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteById(id);
    }
}
