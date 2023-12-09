package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.comment.CommentResponseDto;
import com.literandltx.taskmcapp.dto.comment.CreateCommentRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment manager")
@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Create and assign comment to taskId")
    @PostMapping
    public CommentResponseDto addCommentToTask(
            @RequestBody @Valid CreateCommentRequestDto requestDto,
            @RequestParam Long projectId,
            @RequestParam Long taskId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return commentService.createComment(requestDto, user, projectId, taskId);
    }

    @Operation(summary = "Retrieve all comments assigned to task")
    @GetMapping
    public List<CommentResponseDto> retrieveCommentsForTask(
            @RequestParam Long projectId,
            @RequestParam Long taskId,
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();

        return commentService.findAllByTask(pageable, user, projectId, taskId);
    }
}
