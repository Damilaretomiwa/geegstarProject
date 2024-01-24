package com.rdi.geegstar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdi.geegstar.dto.requests.GetAllTalentsRequest;
import com.rdi.geegstar.dto.requests.RegistrationRequest;
import com.rdi.geegstar.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String URL = "/api/v1/user";

    @Test
    public void testRegistration() {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Retnaa");
        registrationRequest.setLastName("Dayok");
        registrationRequest.setUsername("Darda");
        registrationRequest.setEmail("dayokr@gmail.com");
        registrationRequest.setPhoneNumber("07031005737");
        registrationRequest.setPassword("password");
        registrationRequest.setRole(Role.TALENT);

        try {
            mockMvc.perform(
                            post(URL)
                                    .content(mapper.writeValueAsString(registrationRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testRequestConfirmationCode() {
        try {
            String userEmail = "dayokr@gmail.com";
            mockMvc.perform(
                            post(String.format("%s/confirmation/%s",URL, userEmail))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @Sql("/db/insert.sql")
    public void testConfirmingEmail() {
        String userEmail = "john499@qianhost.com";
        String code = "4532";
        try {
            mockMvc.perform(
                            post(String.format("%s/confirmation/%s/%s", URL, userEmail,code))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testGetUserById() {
        Long userId = 104L;
        try{
            mockMvc.perform(
                            MockMvcRequestBuilders.get(String.format("%s/%d", URL, userId))
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @Sql("/db/insertUsers.sql")
    public void testGetAllTalents() {
        ObjectMapper mapper = new ObjectMapper();
        GetAllTalentsRequest getAllTalentsRequest = new GetAllTalentsRequest();
        int pageNumber = 1;
        int pageSize = 1;
        getAllTalentsRequest.setPageNumber(pageNumber);
        getAllTalentsRequest.setPageSize(pageSize);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.get(String.format("%s/talents", URL))
                                    .content(mapper.writeValueAsString(getAllTalentsRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
