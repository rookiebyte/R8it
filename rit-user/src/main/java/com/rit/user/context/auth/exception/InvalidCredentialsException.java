package com.rit.user.context.auth.exception;


import com.rit.starterboot.servlet.configuration.exception.UnauthorizedException;
import com.rit.user.exception.UserExceptionRepository;

public class InvalidCredentialsException extends UnauthorizedException {

    public InvalidCredentialsException() {
        super(UserExceptionRepository.INVALID_CREDENTIALS_EXCEPTION);
    }
}
