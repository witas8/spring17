package com.example.spring17.curiosity.controller;

import com.example.spring17.controller.CuriosityController;
import com.example.spring17.mapper.CuriosityMapper;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.dto.CuriositySaveDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.security.service.SecurityService;
import com.example.spring17.service.curiosity.CuriosityServiceDeleter;
import com.example.spring17.service.curiosity.CuriosityServiceSelector;
import com.example.spring17.service.curiosity.CuriosityServiceSever;
import com.example.spring17.service.curiosity.CuriosityServiceUpdater;
import com.example.spring17.service.user.UserServiceDeleter;
import com.example.spring17.service.user.UserServiceSaver;
import com.example.spring17.service.user.UserServiceSelector;
import com.example.spring17.service.user.UserServiceUpdater;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.apache.catalina.startup.Tomcat;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@WebMvcTest
public class CuriosityControllerTest {

    @MockBean
    private CuriosityServiceSever curiosityServiceSever;

    @MockBean
    private CuriosityServiceSelector curiosityServiceSelector;

    @MockBean
    private CuriosityServiceUpdater curiosityServiceUpdater;

    @MockBean
    private CuriosityServiceDeleter curiosityServiceDeleter;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private UserServiceSelector userServiceSelector;

    @MockBean
    private UserServiceSaver userServiceSaver;

    @MockBean
    private UserServiceUpdater userServiceUpdater;

    @MockBean
    private UserServiceDeleter userServiceDeleter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //@LocalServerPort
    //private int port;

    @Test
    public void shouldSaveCuriosity() throws Exception {
        //UserSaveDTO(String firstName, String lastName, String username, String password, String role,
        //      String email, String phone, String likedCuriosityIDs)
//        UserSaveDTO userSaveDTO = new UserSaveDTO("mik", "wit", "pass1",
//                "mik1", "ADMIN", "mik@gmail.com", "523333221", "");
//        userRepo.save(userMapper.mapSaveDtoToEntity(userSaveDTO, passwordEncoder));
//
//        User user = userRepo.findByUsername(userSaveDTO.username())
//                .orElseThrow(() -> new RuntimeException("User does not exist!"));

        //UserDTO(@Nullable Long id, String firstName, String lastName, String username, String role,
        //                      String email, String phone, String likedCuriosityIDs)
        UserDTO userDTO = new UserDTO(1L, "mik", "wit", "mik1", "ADMIN",
            "mik@gmial.com", "423555333", "");

        //CuriositySaveDTO(UserDTO userDTO, String category, String question, String answer,
        //      boolean accepted, int likes)
        CuriositySaveDTO curiositySaveDTO = new CuriositySaveDTO(userDTO, "BIOLOGY",
                "Why?", "Because...", true, 8);
        given(curiosityServiceSever.saveCuriosity(any(CuriositySaveDTO.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

//        TestRestTemplate restTemplate = new TestRestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//
//        HttpEntity<CuriositySaveDTO> entity = new HttpEntity<CuriositySaveDTO>(curiositySaveDTO, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:8080/spring17/curiosity/save",
//                HttpMethod.POST, entity, String.class);


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8080/spring17/curiosity/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curiositySaveDTO)));

        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question",
                        CoreMatchers.is(curiositySaveDTO.question())));

    }

}
