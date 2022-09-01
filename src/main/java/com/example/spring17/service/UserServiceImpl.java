package com.example.spring17.service;

import com.example.spring17.model.User;
import com.example.spring17.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        return userRepo.save(user);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by name " + username + " does not exist."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.findById(id).ifPresent(userRepo::delete);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

//    @Override
//    public Optional<User> updateUserEmail(User user) {
//       userRepo.findById(user.getId()).ifPresent(u -> user.setEmail(user.getEmail()));
//       return userRepo.findById(user.getId());
//    }

}
