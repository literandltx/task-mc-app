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
    public void retrieveUsersProjects() {

    }

    @GetMapping("/{id}")
    public void retrieveProjectDetails(@PathVariable Long id) {

    }

    @PutMapping("/{id}")
    public void updateProject(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {

    }
}
