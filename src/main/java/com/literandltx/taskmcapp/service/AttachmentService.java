package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.attachment.DownloadAttachmentRequestDto;
import com.literandltx.taskmcapp.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AttachmentService {
    Boolean uploadAttachment(
            Long taskId,
            User user,
            MultipartFile file
    );

    void downloadAttachment(
            DownloadAttachmentRequestDto requestDto,
            HttpServletResponse response,
            User user
    );
}
