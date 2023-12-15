package com.literandltx.taskmcapp.exception.custom;

public class DropboxException extends RuntimeException {
    public DropboxException(final String message) {
        super(message);
    }

    public DropboxException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
