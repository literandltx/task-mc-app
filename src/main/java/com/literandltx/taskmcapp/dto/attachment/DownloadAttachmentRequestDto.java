package com.literandltx.taskmcapp.dto.attachment;

import lombok.Data;

@Data
public class DownloadAttachmentRequestDto {
    private Long taskId;
    private String filename;
}
