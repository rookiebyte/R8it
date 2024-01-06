package com.rit.user.exception;

import com.rit.starterboot.servlet.configuration.exception.ValidationException;

import java.util.UUID;

import static com.rit.user.exception.UserExceptionRepository.USER_NOT_FOUND_EXCEPTION;


public class UserNotFoundException extends ValidationException {

    public UserNotFoundException(UUID id) {
        super(USER_NOT_FOUND_EXCEPTION, id);
    }
}
