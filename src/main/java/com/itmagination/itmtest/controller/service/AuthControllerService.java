package com.itmagination.itmtest.controller.service;

import com.itmagination.itmtest.controller.requests.LoginRequest;
import com.itmagination.itmtest.security.InvalidCredentialsException;
import com.itmagination.itmtest.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthControllerService {

    private final TokenProvider tokenProvider;

    /**
     * This is just a dummy implementation for this project purposes.
     */
    public String login(LoginRequest request) {
        if (!request.password().equals("Test12345")) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.createToken(request.username());
    }
}
