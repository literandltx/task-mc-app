package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(final Pageable pageable, final Long taskId);
}
