package com.github.vladimirpokhodnya.authjwtapi.resttest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPostEndpoint() throws Exception {
        String jsonRequest = """
                {
                    "name": "name",
                    "email": "email",
                    "password": "password"
                }
                """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void testLoginEndpoint() throws Exception {
        String jsonRequest = """
                {
                    "name": "name",
                    "password": "password",
                    "email": "email"
                }
                """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        String loginRequest = """
                {
                    "name": "name",
                    "password": "password"
                }
                """;

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

}

