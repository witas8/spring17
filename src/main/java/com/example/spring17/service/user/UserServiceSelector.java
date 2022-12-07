package com.example.spring17.service.user;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.user.dto.UserDTO;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceSelector {

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::mapUserToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username)
                .map(userMapper::mapUserToDTO)
                .orElseThrow(() -> new NotFoundException("User", "username", username));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::mapUserToDTO)
                .orElseThrow(() -> new NotFoundException("User", "email", email));
    }

    public UserDTO getUserById(Long id) {
        userValidator.validateIfExists(id);
        return userRepo.findById(id)
                .map(userMapper::mapUserToDTO)
                .orElseThrow(() -> new NotFoundException("User", "id", id.toString()));
    }

}
