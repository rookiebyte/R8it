package com.rit.user.context.auth

import com.rit.user.context.auth.dto.LoginRequest
import com.rit.user.context.auth.dto.RegisterOtpRequest
import com.rit.user.context.auth.dto.RegisterRequest
import com.rit.user.domain.user.UserOtp
import com.rit.user.domain.user.UsersCredentials

trait AuthRequestFactory {

    LoginRequest getLoginRequest(UsersCredentials user) {
        return new LoginRequest(user.email(), new String(user.password()))
    }

    RegisterOtpRequest getRegisterOtpRequest(UsersCredentials user) {
        return new RegisterOtpRequest(user.email())
    }

    RegisterRequest getRegisterRequest(UsersCredentials user, UserOtp userOtp) {
        return new RegisterRequest(user.email(), new String(user.password()), new String(userOtp.value))
    }
}
