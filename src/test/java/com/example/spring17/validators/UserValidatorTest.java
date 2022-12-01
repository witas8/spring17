package com.example.spring17.validators;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    UserValidator userValidator;

    @Mock
    UserRepo userRepo;

    @Test
    void validatePhoneNumberLength() {
        //given
        String phoneNumber = "33";

        //when
        //then
        assertThatThrownBy(() -> userValidator.validatePhoneNumberLength(phoneNumber))
                .isInstanceOf(BadRequestException.class);
        verify(userRepo, never()).save(any());
    }

}