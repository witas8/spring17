package com.example.spring17.service.user;

import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceSaver {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    private final UserValidator userValidator;

    public User saveUser(UserDTO userDTO) {
        userValidator.validateBeforeSaving(userDTO);
        log.info("Saving new user {} to the database", userDTO.username());
        return userRepo.save(userMapper.mapDtoToEntity(userDTO));
    }
}
