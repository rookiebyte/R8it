package com.rit.user.context.auth;

import com.rit.user.context.auth.dto.LoginRequest;
import com.rit.user.context.auth.dto.LoginResponse;
import com.rit.user.context.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
class AuthEndpoint {

    private final AuthService authService;

    AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping(path = "/client/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
