package com.rit.user.context.auth.exception;


import com.rit.starterboot.servlet.configuration.exception.UnauthorizedException;
import com.rit.user.exception.UserExceptionRepository;

public class IncorrectOtpException extends UnauthorizedException {

    public IncorrectOtpException() {
        super(UserExceptionRepository.INCORRECT_OTP_EXCEPTION);
    }
}
