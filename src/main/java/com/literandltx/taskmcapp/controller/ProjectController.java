package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/projects")
@RestController
public class ProjectController {
    @PostMapping
    public void createNewProject() {

    }

    @GetMapping
    public void retrieveUserProjects() {

    }

    @GetMapping
    public void retrieveProjectDetails(@PathVariable Long id) {

    }

    @PutMapping
    public void updateProject(@PathVariable Long id) {

    }

    @DeleteMapping
    public void deleteProject(@PathVariable Long id) {

    }
}
