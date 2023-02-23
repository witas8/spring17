package com.example.spring17.integrations;

import com.example.spring17.controller.CuriosityController;
import com.example.spring17.model.curiosity.entity.Curiosity;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
//import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ComponentScan(basePackages = "com.example.spring17")
@Import(CuriosityController.class)
@Slf4j
public class CuriosityControllerIntTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Sql("/sql/get_all.sql")
    @Test
    void getAllData(){
        MockMvcResponse mvcResponse = given()
                .webAppContextSetup(context)
                .when()
                .get("/spring17/curiosity/all")
                .thenReturn();

        assertThat(mvcResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        List<Curiosity> response = mvcResponse.getBody().as(new TypeRef<>() {});
        log.info(response.toString());
        assertThat(response).hasSize(1);
    }

/*    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }*/

   /* @Test
    void getAll() throws Exception {
        ////http://localhost:8080/spring17/curiosity/all
        RequestBuilder request = get("http://localhost:8080/spring17/curiosity/all");
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn(); //andReturn();
        MockHttpServletResponse response = result.getResponse();
        String contentAsString = response.getContentAsString();
        System.out.println("--> " + contentAsString);
        //assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertThat(contentAsString, contentAsString.length()>10);

        *//*MockMvcResponse mvcResponse = given()
                .mockMvc(mockMvc)
                .when()
                .get("http://localhost:8080/spring17/curiosity/all")
                .thenReturn();
        System.out.println("--> " + mvcResponse.getMvcResult().getResponse().getContentAsString());
        Assertions.assertThat(mvcResponse.getStatusCode()).isEqualTo(HttpStatus.OK.value());*//*


    }*/

}
