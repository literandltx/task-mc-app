package com.literandltx.taskmcapp.exception.custom;

public class DropboxException extends RuntimeException {
    public DropboxException(String message) {
        super(message);
    }

    public DropboxException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
