package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/attachments")
@RestController
public class AttachmentController {
    @PostMapping
    public void uploadAttachmentToTask() {

    }

    @GetMapping("/{taskId}")
    public void retrieveAttachmentsForTask(@PathVariable Long taskId) {

    }
}
