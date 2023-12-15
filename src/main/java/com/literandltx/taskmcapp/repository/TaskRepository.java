package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(final Pageable pageable, final Long projectId);

    Optional<Task> findByIdAndProjectId(final Long id, final Long projectId);

    Boolean existsByIdAndProjectId(final Long id, final Long projectId);

    @Modifying
    @Query(
            value = "INSERT INTO tasks_labels (task_id, label_id) VALUES (:taskId, :labelId)",
            nativeQuery = true
    )
    void assignLabelToTask(final Long taskId, final Long labelId);

    @Query(
            value = "UPDATE tasks_labels SET is_deleted=true "
                    + "WHERE label_id=:labelId AND task_id=:taskId",
            nativeQuery = true
    )
    void removeLabelFromTask(final Long taskId, final Long labelId);

    @Query(
            value = "SELECT"
                    + "    label.id, "
                    + "    label.name, "
                    + "    label.color "
                    + "FROM "
                    + "    labels label "
                    + "        INNER JOIN projects project ON project.id = label.project_id "
                    + "        INNER JOIN tasks_labels tl ON label.id = tl.label_id "
                    + "WHERE "
                    + "    tl.is_deleted=false AND task_id=:taskId  ",
            nativeQuery = true
    )
    List<String> findAllAssignedLabels(final Long taskId);
}
