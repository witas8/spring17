package com.example.spring17.user;

import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.Roles;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceUpdater;
import com.example.spring17.validators.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUpdaterTest {

    @InjectMocks
    UserServiceUpdater userServiceUpdaterTest;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidator userValidator;

    @Test
    @DisplayName("Should update user if exists")
    void shouldUpdateUser(){
        //given
//        Long id =
//        given(userRepo.findById()).willReturn()

        //given
        User user = new User(5L, "a", "b", "c", "d", Roles.ADMIN, "f", "g", "h");
        UserDTO userDTO = new UserDTO(5L, "a", "b", "c", Roles.ADMIN.toString(), "f", "g", "h");
        given(userRepo.findById(5L)).willReturn(Optional.of(user));
        given(userRepo.save(user)).willReturn(user);


        //when
        when(userMapper.mapUserToDTO(any(User.class))).thenReturn(userDTO);
        when(userMapper.mapUpdatedDtoToEntity(any(UserDTO.class), any(User.class))).thenReturn(user);
        userServiceUpdaterTest.updateUser(5L, userDTO);

        //then
        verify(userRepo).findById(5L);


    }

}
