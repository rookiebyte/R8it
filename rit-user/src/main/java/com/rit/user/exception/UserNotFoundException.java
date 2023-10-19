package com.rit.user.exception;

import com.rit.starterboot.configuration.exception.ValidationException;

import static com.rit.user.exception.UserExceptionRepository.USER_NOT_FOUND_EXCEPTION;


public class UserNotFoundException extends ValidationException {

    public UserNotFoundException(String id) {
        super(USER_NOT_FOUND_EXCEPTION, id);
    }
}
