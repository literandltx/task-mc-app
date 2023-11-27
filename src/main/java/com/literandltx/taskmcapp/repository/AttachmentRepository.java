package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Attachment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @EntityGraph(attributePaths = {"task", "task.project", "task.project.user"})
    List<Attachment> findAllByTaskId(Long taskId);
}
