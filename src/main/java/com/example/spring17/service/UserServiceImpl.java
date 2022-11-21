package com.example.spring17.service;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
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
        Boolean doesUsernameExist = userRepo.checkUsernameUniqueness(user.getUsername());
        Boolean doesEmailExist = userRepo.checkEmailUniqueness(user.getEmail());

        if (doesUsernameExist) {
            throw new BadRequestException(
                    "Username " + user.getUsername() + " is already taken");
        }

        if (doesEmailExist) {
            throw new BadRequestException(
                    "Email " + user.getEmail() + " is already taken");
        }

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
    public Optional<User> getUserById(Long id) {
        if(!userRepo.existsById(id)) {
            throw new NotFoundException(
                    "User with id " + id + " does not exists");
        }

        return userRepo.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        if(!userRepo.existsById(id)) {
            throw new NotFoundException(
                    "User with id " + id + " does not exists");
        }

        User user = userRepo.findById(id).orElseThrow(null);
        log.info("User with id {}, name {}, last name {} and email {} is deleted from the database.",
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        userRepo.findById(id).ifPresent(userRepo::delete);
    }

    @Override
    public Optional<User> updateUser(Long id, User updatedUser) {
        User userToBeUpdated = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " does not exist."));
        if(userToBeUpdated != null) {
            if(updatedUser.getFirstName() != null) userToBeUpdated.setFirstName(updatedUser.getFirstName());
            if(updatedUser.getLastName() != null) userToBeUpdated.setLastName(updatedUser.getLastName());
            if(updatedUser.getUsername() != null) userToBeUpdated.setUsername(updatedUser.getUsername());
            if(updatedUser.getRole() != null) userToBeUpdated.setRole(updatedUser.getRole());
            if(updatedUser.getEmail() != null) userToBeUpdated.setEmail(updatedUser.getEmail());
            if(updatedUser.getPhone() != null) userToBeUpdated.setPhone(updatedUser.getPhone());
            userRepo.findById(id).ifPresent(userRepo::save);
        }

        log.info("User with id {} has been updated ", id);
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updatePassword(Long id, String password) {
        if(password != null){
            userRepo.findById(id).ifPresentOrElse(u -> u.setPassword(password),
                    () -> { throw new NotFoundException("User by id " + id + " does not exist."); }
            );
            userRepo.findById(id).ifPresent(userRepo::save);
        }

        log.info("User with id {} has updated a password", id);
        return userRepo.findById(id);
    }
}
