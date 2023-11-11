package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tasks")
@RestController
public class TaskController {
    @PostMapping
    public void CreateNewTask() {

    }

    @GetMapping
    public void retrieveTasksProjects() {

    }

    @GetMapping("/{id]")
    public void retrieveTaskDetails(@PathVariable Long id) {

    }

    @PutMapping("{/id}")
    public void updateTask(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {

    }
}
