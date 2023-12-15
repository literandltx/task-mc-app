package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.Confirmation;
import com.literandltx.taskmcapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Boolean existsByTokenAndUser(final String token, final User user);
}
