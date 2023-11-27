package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.model.Attachment;
import com.literandltx.taskmcapp.model.Task;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.AttachmentRepository;
import com.literandltx.taskmcapp.repository.TaskRepository;
import com.literandltx.taskmcapp.service.dropbox.DropboxService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final DropboxService dropboxService;

    @Override
    public Boolean uploadAttachment(
            Long taskId,
            User user,
            MultipartFile file
    ) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException("Cannot find task by id: " + taskId));

        if (!task.getProject().getUser().getId().equals(user.getId())) {
            throw new RuntimeException();
        }

        String filePath = "/" + taskId + "-" + file.getOriginalFilename();

        try {
            dropboxService.uploadFile(filePath, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Attachment model = new Attachment();
        model.setDropboxFilename(filePath);
        model.setFilename(file.getOriginalFilename());
        model.setTask(task);

        attachmentRepository.save(model);

        return true;
    }

    @Override
    public void downloadAttachment(
            DownloadAttachmentRequestDto requestDto,
            HttpServletResponse response,
            User user
    ) {
        String dropboxFilename = "/" + requestDto.getTaskId() + "-" + requestDto.getFilename();

        InputStream inputStream = dropboxService.downloadFile(dropboxFilename);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + requestDto.getFilename());

        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot download file: " + requestDto.getFilename());
        }
    }
}
