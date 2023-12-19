package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.exception.custom.AttachmentException;
import com.literandltx.taskmcapp.exception.custom.PermissionDeniedException;
import com.literandltx.taskmcapp.model.Attachment;
import com.literandltx.taskmcapp.model.Task;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.AttachmentRepository;
import com.literandltx.taskmcapp.repository.TaskRepository;
import com.literandltx.taskmcapp.service.app.AttachmentService;
import com.literandltx.taskmcapp.service.dropbox.DropboxService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final DropboxService dropboxService;

    @Override
    public ResponseEntity<Void> uploadAttachment(
            final Long taskId,
            final User user,
            final MultipartFile file
    ) {
        final Optional<Task> task = taskRepository.findById(taskId);

        if (task.isEmpty()) {
            log.info(String.format(
                    "User: %s, have not taskId: %s.",
                    user.getUsername(), taskId));
            throw new EntityNotFoundException(String.format(
                    "User: %s, have not taskId: %s.",
                    user.getUsername(), taskId));
        }

        if (!task.get().getProject().getUser().getId().equals(user.getId())) {
            log.info(String.format(
                    "User: %s, have no access to taskId: %s",
                    user.getUsername(), taskId));
            throw new PermissionDeniedException(String.format(
                    "User: %s, have no access to taskId: %s",
                    user.getUsername(), taskId));
        }

        final String filePath = "/" + taskId + "-" + file.getOriginalFilename();

        try {
            dropboxService.uploadFile(filePath, file.getInputStream());
        } catch (IOException e) {
            log.info(String.format(
                    "User: %s, Cannot upload to dropbox file: %s, from taskId: %s, attachment",
                    user.getUsername(), file.getOriginalFilename(), taskId));
            throw new AttachmentException(String.format(
                    "User: %s, Cannot upload to dropbox file: %s, from taskId: %s, attachment",
                    user.getUsername(), file.getOriginalFilename(), taskId), e);
        }

        final Attachment model = new Attachment();
        model.setDropboxFilename(filePath);
        model.setFilename(file.getOriginalFilename());
        model.setTask(task.get());

        attachmentRepository.save(model);

        return ResponseEntity.ok().build();
    }

    @Override
    public void downloadAttachment(
            final DownloadAttachmentRequestDto requestDto,
            final HttpServletResponse response,
            final User user
    ) {
        final String dropboxFile = "/" + requestDto.getTaskId() + "-" + requestDto.getFilename();

        final InputStream inputStream = dropboxService.downloadFile(dropboxFile);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + requestDto.getFilename());

        try (OutputStream outputStream = response.getOutputStream()) {
            final byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.info(String.format(
                    "User: %s, cannot download file: %s, from taskId: %s, attachment",
                    user.getUsername(), requestDto.getFilename(), requestDto.getTaskId()));
            throw new AttachmentException(String.format(
                    "User: %s, cannot download file: %s, from taskId: %s, attachment",
                    user.getUsername(), requestDto.getFilename(), requestDto.getTaskId()), e);
        }

        log.info(String.format(
                "User: %s, downloaded file: %s, from taskId: %s, attachment.",
                user.getUsername(), requestDto.getFilename(), requestDto.getTaskId()));
    }
}
