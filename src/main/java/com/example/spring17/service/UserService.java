package com.example.spring17.service;

import com.example.spring17.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    Optional <User> getUserById(Long id);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    Optional<User> updateUser(Long id, User user);
    Optional<User> updatePassword(Long id, String password);

}
