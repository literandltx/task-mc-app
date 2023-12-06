package com.literandltx.taskmcapp.exception.custom;

public class AttachmentException extends RuntimeException {

    public AttachmentException(final String message) {
        super(message);
    }

    public AttachmentException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
