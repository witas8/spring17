package com.example.spring17.user;

import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceSelector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceSelectorTest {

    @InjectMocks
    UserServiceSelector userServiceSelectorTest;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Should get all users")
    void shouldGetAllUsers() {
        //when
        userServiceSelectorTest.getAllUsers();

        // then
        verify(userRepo).findAll();
    }

}
