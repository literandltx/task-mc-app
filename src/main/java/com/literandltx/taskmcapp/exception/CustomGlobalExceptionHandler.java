package com.literandltx.taskmcapp.exception;

import com.dropbox.core.DbxException;
import com.literandltx.taskmcapp.exception.custom.AttachmentException;
import com.literandltx.taskmcapp.exception.custom.DropboxException;
import com.literandltx.taskmcapp.exception.custom.PermissionDeniedException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({ PSQLException.class })
    protected ResponseEntity<Object> handlePostgresException(
            final PSQLException ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Postgres error occurred: " + ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFoundException(
            final EntityNotFoundException ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ DropboxException.class, DbxException.class })
    protected ResponseEntity<Object> handleDropboxException(
            final DropboxException ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Dropbox error occurred: " + ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AttachmentException.class })
    protected ResponseEntity<Object> handleAttachmentException(
            final AttachmentException ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Attachment service error occurred: " + ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ PermissionDeniedException.class })
    protected ResponseEntity<Object> handleUserDataPermissionDeniedException(
            final PermissionDeniedException ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "" + ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleUserDataPermissionDeniedException(
            final Exception ex,
            final WebRequest request
    ) {
        final Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    private String getErrorMessage(final ObjectError e) {
        if (e instanceof FieldError) {
            final String field = ((FieldError) e).getField();
            final String message = e.getDefaultMessage();

            return field + " " + message;
        }

        return e.getDefaultMessage();
    }

}
