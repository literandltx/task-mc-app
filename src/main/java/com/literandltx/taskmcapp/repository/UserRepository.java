package com.literandltx.taskmcapp.repository;

import com.literandltx.taskmcapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User users INNER JOIN FETCH users.roles WHERE users.username = :username")
    Optional<User> findByUsername(String username);
}
