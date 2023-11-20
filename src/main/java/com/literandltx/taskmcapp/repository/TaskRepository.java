package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(Pageable pageable, Long projectId);

    Optional<Task> findByIdAndProjectId(Long id, Long projectId);

    Boolean existsByIdAndProjectId(Long id, Long projectId);
}
