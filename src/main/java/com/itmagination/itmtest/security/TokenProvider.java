package com.itmagination.itmtest.security;

import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    public String createToken(String username) {
        return String.format("AUTH_KEY_%s", username);
    }

    boolean isTokenValid(String token) {
        return token.startsWith("AUTH_KEY");
    }

    String getUsernameFromToken(String token) {
        return token.substring(8);
    }

    String extractToken(String httpHeader) {
        if (httpHeader != null && httpHeader.startsWith("Bearer ")) {
            return httpHeader.substring(7);
        } else return null;
    }
}
