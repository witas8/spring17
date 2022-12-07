package com.example.spring17.service.user;

import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.user.dto.UserSaveDTO;
import com.example.spring17.model.curiosity.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserSaveDTO userSaveDTO) {
        userValidator.validateBeforeSaving(userSaveDTO);
        log.info("Saving new user {} to the database", userSaveDTO.username());
        return userRepo.save(userMapper.mapSaveDtoToEntity(userSaveDTO, passwordEncoder));
    }
}
