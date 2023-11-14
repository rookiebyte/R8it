package com.rit.user.context.auth

import com.rit.user.context.auth.dto.LoginRequest
import com.rit.user.context.auth.dto.RegisterOtpRequest
import com.rit.user.context.auth.dto.RegisterRequest
import com.rit.user.domain.user.User
import com.rit.user.domain.user.UsersCredentials

trait AuthRequestFactory {

    LoginRequest getLoginRequest(UsersCredentials user) {
        return new LoginRequest(user.email(), user.passwordAsString())
    }

    RegisterOtpRequest getRegisterOtpRequest(UsersCredentials user, String otp) {
        return new RegisterOtpRequest(user.email(), otp)
    }

    RegisterRequest getRegisterRequest(User user, UsersCredentials credentials) {
        return new RegisterRequest(user.getEmail(),
                user.getUsername(),
                credentials.passwordAsString())
    }
}
