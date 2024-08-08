package com.itmagination.itmtest.controller.requests;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty String username,
        @NotEmpty String password
) {
}
