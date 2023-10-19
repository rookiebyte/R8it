package com.rit.user.context.auth.exception;


import com.rit.starterboot.configuration.exception.ValidationException;
import com.rit.user.exception.UserExceptionRepository;

public class UserAlreadyExistsException extends ValidationException {

    public UserAlreadyExistsException(String username) {
        super(UserExceptionRepository.USER_ALREADY_EXISTS_EXCEPTION, username);
    }
}
