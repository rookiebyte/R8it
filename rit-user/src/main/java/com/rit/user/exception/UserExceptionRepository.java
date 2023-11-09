package com.rit.user.exception;

import com.rit.starterboot.configuration.exception.RestExceptionCode;

public final class UserExceptionRepository {

    public static final RestExceptionCode INCORRECT_OTP_EXCEPTION = new RestExceptionCode("USER-003",
            "Incorrect otp code");
    public static final RestExceptionCode USER_NOT_FOUND_EXCEPTION = new RestExceptionCode("USER-002",
            "User with id %s not found!");

    public static final RestExceptionCode USER_ALREADY_EXISTS_EXCEPTION = new RestExceptionCode("USER-001",
            "User with email %s already exists!");

    public static final RestExceptionCode INVALID_CREDENTIALS_EXCEPTION = new RestExceptionCode("USER-000",
            "Incorrect email or password!");

    private UserExceptionRepository() {
    }
}
