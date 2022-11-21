package com.example.spring17.user;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.model.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    //@Autowired
    UserServiceImpl userServiceTest;

    @BeforeEach
    void setUp(){
        userServiceTest = new UserServiceImpl(userRepo);
    }

    @Test
    void shouldGetAllUsers() {
        // when
        userServiceTest.getAllUsers();

        // then
        verify(userRepo).findAll();
    }

    @Test
    void shouldSaveUser(){
        //given
        User user = new User(101L, "testFirstName", "testLastName",
                "testUsername", "testPassword", "admin",
                "test@gmail.com", "500600700");

        //when
        userServiceTest.saveUser(user);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);

    }

    @Test
    void shouldThrowWhenUsernameIsTaken(){
        //given
        User user = new User(102L, "mik", "testLastName",
                "mikwit8", "testPassword", "admin",
                "test8@gmail.com", "500600700");
        given(userRepo.checkUsernameUniqueness(user.getUsername())).willReturn(true);

        //when
        assertThatThrownBy(() -> userServiceTest.saveUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Username " + user.getUsername() + " is already taken");
        verify(userRepo, never()).save(any());

    }

    @Test
    void shouldDeleteUser(){
        //given
        Long id = 1L;
        given(userRepo.existsById(id)).willReturn(true);

        //when
        userServiceTest.deleteUserById(id);

        //then
        verify(userRepo).deleteById(id);
    }



}
