package com.example.spring17.user;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.Roles;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceSaver;
import com.example.spring17.validators.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static com.example.spring17.utils.Constants.BAD_REQUEST_TAKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceSaverTest {

    @InjectMocks
    private UserServiceSaver userServiceSaver;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should save user")
    void shouldSaveUser(){
        //given
        User user = new User(101L, "testFirstName", "testLastName",
                "testUsername", "testPassword", Roles.ADMIN,
                "test@gmail.com", "500600700", "");
        UserSaveDTO userSaveDTO = new UserSaveDTO("mik", "testLastName", "mikwit8",
                "pass", Roles.ADMIN.toString(), "test8@gmail.com", "500600700", "");

        //when
        when(userMapper.mapSaveDtoToEntity(any(UserSaveDTO.class), eq(passwordEncoder))).thenReturn(user);
        userServiceSaver.saveUser(userSaveDTO); //userMapper.mapUserToDTO(user)

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
        UserSaveDTO userSaveDTO = new UserSaveDTO("mik", "testLastName", "mikwit8",
                "pass", Roles.ADMIN.toString(), "test8@gmail.com", "500600700", "");

        //when
        doThrow(new BadRequestException("Username", userSaveDTO.username(), true))
                .when(userValidator).validateBeforeSaving(userSaveDTO);

        //then
       assertThatThrownBy(() -> userServiceSaver.saveUser(userSaveDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Username " + userSaveDTO.username() + BAD_REQUEST_TAKEN);
        verify(userRepo, never()).save(any());

    }

}
