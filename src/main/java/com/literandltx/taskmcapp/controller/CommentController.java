package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @PostMapping
    public void addCommentToTask() {

    }

    @GetMapping("/{taskId}")
    public void retrieveCommentsForTask(@PathVariable Long taskId) {

    }
}
