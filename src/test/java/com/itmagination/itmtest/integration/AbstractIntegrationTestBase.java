package com.itmagination.itmtest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmagination.itmtest.controller.requests.LoginRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(TestContainersConfiguration.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public abstract class AbstractIntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected IdentifiedUser adminUser = new IdentifiedUser("admin", "Test12345");

    protected RequestPostProcessor withUser(IdentifiedUser identifiedUser) throws Exception {
        return withUser(identifiedUser.username(), identifiedUser.password());
    }

    protected RequestPostProcessor withUser(String username, String password) throws Exception {
        MvcResult result = login(username, password);
        String token = result.getResponse().getContentAsString();
        return request -> {
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        };
    }

    protected MvcResult login(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        String jsonRequest = objectMapper.writeValueAsString(request);
        return mockMvc.perform(
                post("/api/public/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
}
