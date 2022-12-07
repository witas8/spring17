package com.example.spring17.user;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.user.dto.UserDTO;
import com.example.spring17.model.curiosity.user.entity.Roles;
import com.example.spring17.model.curiosity.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceSaver;
import com.example.spring17.validators.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.example.spring17.utils.Constants.BAD_REQUEST_TAKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceSaverTest {

    @InjectMocks
    private UserServiceSaver userServiceSaverTest;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserMapper userMapper;


    @Test
    @DisplayName("Should save user")
    void shouldSaveUser(){
        //given
        User user = new User(101L, "testFirstName", "testLastName",
                "testUsername", "testPassword", Roles.ADMIN,
                "test@gmail.com", "500600700");

        //when
        when(userMapper.mapUserToDTO(any(User.class))).thenReturn(
                new UserDTO(101L, "testFirstName", "testUsername",
                "testUsername", Roles.ADMIN.toString(), "test@gmail.com", "500600700"));
        when(userMapper.mapDtoToEntity(any(UserDTO.class))).thenReturn(user);
        userServiceSaverTest.saveUser(userMapper.mapUserToDTO(user));

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);

    }

    @Test
    @DisplayName("Should throw user when username is taken")
    void shouldThrowWhenUsernameIsTaken(){
        //given
        UserDTO userDTO = new UserDTO(101L,"mik", "testLastName",
                "mikwit8", Roles.ADMIN.toString(), "test8@gmail.com", "500600700");

        //when
        doThrow(new BadRequestException("Username", userDTO.username(), true)).when(userValidator).validateUserExistence(userDTO.username());

        //then
       assertThatThrownBy(() -> userServiceSaverTest.saveUser(userDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Username " + userDTO.username() + BAD_REQUEST_TAKEN);
        verify(userRepo, never()).save(any());

    }

}
