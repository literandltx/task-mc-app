package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser(final Pageable pageable, final User user);

    Optional<Project> findByIdAndUserId(final Long id, final Long userId);

    Boolean existsByIdAndUser(final Long id, final User user);
}
