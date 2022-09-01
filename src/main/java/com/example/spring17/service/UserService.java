package com.example.spring17.service;

import com.example.spring17.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    List<User> getAllUsers();
    void deleteUserById(Long id);
    void deleteUser(User user);
    //Optional<User> updateUserEmail(User user);
}
