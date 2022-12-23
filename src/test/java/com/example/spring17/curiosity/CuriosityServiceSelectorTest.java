package com.example.spring17.curiosity;

import com.example.spring17.model.curiosity.entity.Categories;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.entity.Roles;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.curiosity.CuriosityServiceSelector;
import com.example.spring17.service.curiosity.CuriosityServiceSever;
import com.example.spring17.service.user.UserServiceSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CuriosityServiceSelectorTest {

    @InjectMocks
    CuriosityServiceSelector curiosityServiceSelectorTest;
    CuriosityServiceSever curiosityServiceSeverTest;

    @Mock
    UserRepo userRepo;

    @Mock
    UserServiceSelector userService;

    @Mock
    CuriosityRepo curiosityRepo;

    @Test
    void shouldGetUserByCuriosityQuestion(){
        //given
        User user = new User(102L, "mik", "testLastName",
                "mikwit8", "testPassword", Roles.ADMIN,
                "test88@gmail.com", "500600700");
        Curiosity curiosity = new Curiosity(103L, user, Categories.BIOLOGY,
                "que", "ans", false, 10, OffsetDateTime.now());

        //when
        given(curiosityRepo.checkQuestionUniqueness(curiosity.getQuestion())).willReturn(false);
        //given(curiosityRepo.existsById(103L)).willReturn(true);
        //given(curiosityRepo.findAll().stream().anyMatch(c -> c.getQuestion().equals("que"))).willReturn(false);

        assert curiosity.getUser().getId() != null;
        given(userRepo.existsById(curiosity.getUser().getId())).willReturn(false);
        //curiosityServiceSeverTest.saveCuriosity(curiosity);

        //then
        assertThat(curiosityServiceSelectorTest.getUserDTOByQuestionContaining(curiosity.getQuestion()), isNotNull());
    }


}
