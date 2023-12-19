package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AttachmentService {
    ResponseEntity<Void> uploadAttachment(
            final Long taskId,
            final User user,
            final MultipartFile file
    );

    void downloadAttachment(
            final DownloadAttachmentRequestDto requestDto,
            final HttpServletResponse response,
            final User user
    );
}
