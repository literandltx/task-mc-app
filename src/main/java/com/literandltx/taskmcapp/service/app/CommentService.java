package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.comment.CommentResponseDto;
import com.literandltx.taskmcapp.dto.comment.CreateCommentRequestDto;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentResponseDto createComment(
            final CreateCommentRequestDto requestDto,
            final User user,
            final Long projectId,
            final Long taskId
    );

    List<CommentResponseDto> findAllByTask(
            final Pageable pageable,
            final User user,
            final Long projectId,
            final Long taskId
    );
}
