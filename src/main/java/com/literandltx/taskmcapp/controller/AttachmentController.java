package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.attachment.AttachmentResponseDto;
import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.mapper.AttachmentMapper;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.AttachmentService;
import com.literandltx.taskmcapp.service.dropbox.DropboxService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/attachments")
@RestController
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;
    private final DropboxService dropboxService;

    @PostMapping
    public AttachmentResponseDto uploadAttachmentToTask(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam Long taskId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return attachmentMapper.toDto(attachmentService.uploadAttachment(taskId, user, file));
    }

    @GetMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})// MediaType.png
    public byte[] retrieveAttachmentsForTask(
            @RequestBody DownloadAttachmentRequestDto requestDto,
            HttpServletResponse response,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        attachmentService.downloadAttachment(requestDto, response, user);

        InputStream inputStream = dropboxService.downloadFile(requestDto.getFilename());

        try {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
