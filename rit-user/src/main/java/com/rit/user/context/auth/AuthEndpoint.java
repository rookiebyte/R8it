package com.rit.user.context.auth;

import com.rit.user.context.auth.dto.LoginRequest;
import com.rit.user.context.auth.dto.LoginResponse;
import com.rit.user.context.auth.dto.RegisterOtpRequest;
import com.rit.user.context.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
class AuthEndpoint {

    private final AuthService authService;

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    void registerInit(@Valid @RequestBody RegisterRequest request) {
        authService.registerInit(request);
    }

    @PostMapping(path = "/register/otp", consumes = {MediaType.APPLICATION_JSON_VALUE})
    LoginResponse userRegisterConfirmOtp(@Valid @RequestBody RegisterOtpRequest request) {
        return authService.userRegisterConfirmOtp(request);
    }
}
