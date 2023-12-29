package com.rit.starterboot.servlet.configuration.security.jwt.exception;

public class JwtException extends RuntimeException {

    public JwtException(String message) {
        super(message);
    }
}
