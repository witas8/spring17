package com.example.spring17.service.user;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UpdatePasswordDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        userValidator.validateBeforeSaving(updatedUserDTO);
        return userRepo.findById(id)
                .map(user -> userMapper.mapUpdatedDtoToEntity(updatedUserDTO, user))
                .map(userRepo::save)
                .map(userMapper::mapUserToDTO)
                .orElseThrow(() -> new NotFoundException("User", "id", id.toString()));
    }

    //TODO password1 == password2
    public Optional<UserDTO> updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
            userRepo.findById(id).ifPresentOrElse(u -> u.setPassword(updatePasswordDTO.password1()),
                    () -> { throw new NotFoundException("User", "id", id.toString()); }
            );
            userRepo.findById(id).ifPresent(userRepo::save);

        log.info("User with id {} has updated a password", id);
        return userRepo.findById(id).map(userMapper::mapUserToDTO);
    }

}
