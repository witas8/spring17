package com.example.spring17.service.user;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UpdatePasswordDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceUpdater {

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        Optional<User> user = userRepo.findById(id);
        userValidator.validateBeforeUpdating(updatedUserDTO, user);
        return user
                .map(u -> userMapper.mapUpdatedDtoToEntity(updatedUserDTO, u))
                .map(userRepo::save)
                .map(userMapper::mapUserToDTO)
                .orElseThrow(() -> new NotFoundException("User", "id", id.toString()));
    }

    public Optional<UserDTO> updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        if(!updatePasswordDTO.password1().equals(updatePasswordDTO.password2())){
            throw new BadRequestException("User", "password", false);
        } else {
            userRepo.findById(id).ifPresentOrElse(u -> u.setPassword(passwordEncoder.encode(updatePasswordDTO.password1())),
                    () -> { throw new NotFoundException("User", "id", id.toString()); }
            );
            userRepo.findById(id).ifPresent(userRepo::save);
            return userRepo.findById(id).map(userMapper::mapUserToDTO);
        }
    }

}
