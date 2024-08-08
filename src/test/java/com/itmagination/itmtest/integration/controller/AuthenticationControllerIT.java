package com.itmagination.itmtest.integration.controller;

import com.itmagination.itmtest.integration.AbstractIntegrationTestBase;
import com.itmagination.itmtest.controller.requests.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthenticationControllerIT extends AbstractIntegrationTestBase {

    @Test
    void shouldLoginAsAdminUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
                "admin",
                "Test12345"
        );
        mockMvc.perform(
                        post("/api/public/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("AUTH_KEY_admin"));
    }

    @Test
    void shouldNotLoginAsAdminUserDueToWrongPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
                "admin",
                "Test"
        );
        mockMvc.perform(
                post("/api/public/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldNotBeAbleToAccessSecuredEndpoint() throws Exception {
        mockMvc.perform(
                post("/api/v1/anything")
        ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }
}
