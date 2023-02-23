package com.example.spring17.integrations;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.dto.CuriositySaveDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.curiosity.CuriosityServiceSelector;
import com.example.spring17.service.curiosity.CuriosityServiceSever;
import com.example.spring17.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CuriosityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuriosityServiceSelector curiosityServiceSelector;

    @Autowired
    private CuriosityServiceSever curiosityServiceSever;

    @Autowired
    private CuriosityRepo curiosityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Test
    public void shouldSaveCuriosity() throws JsonProcessingException {
        //UserSaveDTO(String firstName, String lastName, String username, String password, String role,
        //      String email, String phone, String likedCuriosityIDs)
        UserSaveDTO userSaveDTO = new UserSaveDTO("mik", "wit", "mik1",
                "pass1", "ADMIN", "mik@gmail.com", "523333221", "");
        userRepo.save(userMapper.mapSaveDtoToEntity(userSaveDTO, passwordEncoder));

        User user = userRepo.findByUsername(userSaveDTO.username())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));


        //CuriositySaveDTO(UserDTO userDTO, String category, String question, String answer,
        //      boolean accepted, int likes)
        CuriositySaveDTO curiositySaveDTO = new CuriositySaveDTO(userMapper.mapUserToDTO(user),
                "BIOLOGY", "Why?", "Because...", true, 8);

//        ResultActions resultActions = mockMvc.perform(post("/spring17/curiosity/save").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(curiositySaveDTO)));

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<CuriositySaveDTO> entity = new HttpEntity<CuriositySaveDTO>(curiositySaveDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/spring17/curiosity/save",
                HttpMethod.POST, entity, String.class);

        System.out.println("--> " + response.toString() );

        assertEquals(response.getStatusCodeValue(), 201);
        assertThat(objectMapper.readValue(response.getBody(), CuriositySaveDTO.class).question())
                .isEqualTo("Why?");

    }

}
