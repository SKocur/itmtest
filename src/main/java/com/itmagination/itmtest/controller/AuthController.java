package com.itmagination.itmtest.controller;

import com.itmagination.itmtest.controller.requests.LoginRequest;
import com.itmagination.itmtest.controller.service.AuthControllerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/v1/auth")
public class AuthController {

    private final AuthControllerService controllerService;

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request) {
        return controllerService.login(request);
    }
}
