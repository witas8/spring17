package com.example.spring17.user;

import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceDeleter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceDeleterTest {

    @InjectMocks
    UserServiceDeleter userServiceDeleterTest;

    @Mock
    private UserRepo userRepo;

    @Test
    @DisplayName("Should delete user if exists")
    void shouldDeleteUser(){
        //given
        Long id = 11L;
        User user = User.builder().id(id).build();
        given(userRepo.findById(id)).willReturn(Optional.of(user));

        //when
        userServiceDeleterTest.deleteUserById(id);

        //then
        verify(userRepo).delete(user);
    }
}
