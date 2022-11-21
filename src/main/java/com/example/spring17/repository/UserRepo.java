package com.example.spring17.repository;

import com.example.spring17.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE u.username = ?1"
    )
        //"FROM User u WHERE u.email = ?1"
    Boolean checkUsernameUniqueness(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u WHERE u.email = ?1"
    )
        //"FROM User u WHERE u.email = ?1"
    Boolean checkEmailUniqueness(String username);

}