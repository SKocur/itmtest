package com.itmagination.itmtest.unit;

import com.itmagination.itmtest.controller.requests.LoginRequest;
import com.itmagination.itmtest.controller.service.AuthControllerService;
import com.itmagination.itmtest.security.InvalidCredentialsException;
import com.itmagination.itmtest.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerServiceTest {

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthControllerService authControllerService;

    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest("admin", "Test12345");
        invalidLoginRequest = new LoginRequest("admin", "WrongPassword");
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        String expectedToken = "dummy-token";
        when(tokenProvider.createToken(validLoginRequest.username())).thenReturn(expectedToken);

        String actualToken = authControllerService.login(validLoginRequest);

        assertEquals(expectedToken, actualToken);
        verify(tokenProvider, times(1)).createToken(validLoginRequest.username());
    }

    @Test
    void login_ShouldThrowInvalidCredentialsException_WhenCredentialsAreInvalid() {
        assertThrows(InvalidCredentialsException.class, () -> {
            authControllerService.login(invalidLoginRequest);
        });

        verify(tokenProvider, never()).createToken(anyString());
    }
}
