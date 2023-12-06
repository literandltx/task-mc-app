package com.literandltx.taskmcapp.exception.custom;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(final String message) {
        super(message);
    }
}
