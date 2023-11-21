package com.literandltx.taskmcapp.mapper;

import com.literandltx.taskmcapp.config.MapperConfig;
import com.literandltx.taskmcapp.dto.comment.CommentResponseDto;
import com.literandltx.taskmcapp.dto.comment.CreateCommentRequestDto;
import com.literandltx.taskmcapp.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    Comment toModel(CreateCommentRequestDto requestDto);

    @Mappings({
            @Mapping(source = "task.id", target = "taskId"),
            @Mapping(source = "user.id", target = "userId")
    })
    CommentResponseDto toDto(Comment comment);
}
