package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Comment;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user", "task"})
    List<Comment> findAllByUserAndTaskId(
            final Pageable pageable,
            final User user,
            final Long taskId
    );

    @EntityGraph(attributePaths = {"user, task"})
    Optional<Comment> findById(final Long id);
}
