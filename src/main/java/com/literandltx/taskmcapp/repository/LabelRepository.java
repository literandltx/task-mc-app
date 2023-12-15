package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Label;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByProjectId(final Pageable pageable, final Long projectId);

    Optional<Label> findByIdAndProjectId(final Long id, final Long projectId);

    Boolean existsByIdAndProjectId(final Long id, final Long projectId);
}
