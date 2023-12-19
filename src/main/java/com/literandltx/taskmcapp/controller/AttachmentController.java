package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.mapper.AttachmentMapper;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.AttachmentService;
import com.literandltx.taskmcapp.service.dropbox.DropboxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Attachment manager")
@RequiredArgsConstructor
@RequestMapping("/attachments")
@RestController
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;
    private final DropboxService dropboxService;

    @Operation(summary = "Upload file for task")
    @PostMapping
    public ResponseEntity<Void> uploadAttachmentToTask(
            @RequestPart("file") final MultipartFile file,
            @RequestParam final Long taskId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        return attachmentService.uploadAttachment(taskId, user, file);
    }

    @Operation(summary = "Download attached task's file")
    @GetMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void retrieveAttachmentsForTask(
            @RequestBody final DownloadAttachmentRequestDto requestDto,
            final HttpServletResponse response,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        attachmentService.downloadAttachment(requestDto, response, user);
    }
}
