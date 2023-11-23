package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Optional<Confirmation> findByToken(String token);
}
