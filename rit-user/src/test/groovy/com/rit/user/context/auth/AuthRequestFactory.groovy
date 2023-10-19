package com.rit.user.context.auth

import com.rit.user.context.auth.dto.LoginRequest
import com.rit.user.context.auth.dto.RegisterRequest
import com.rit.user.domain.user.UsersCredentials

trait AuthRequestFactory {

    LoginRequest getLoginRequest(UsersCredentials user) {
        return new LoginRequest(user.email(), new String(user.password()))
    }

    RegisterRequest getRegisterRequest(UsersCredentials user) {
        return new RegisterRequest(user.email(), new String(user.password()))
    }
}
