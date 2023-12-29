package com.rit.starterboot.servlet.configuration.exception;

public class RestExceptionCode {

    private final String code;
    private final String message;

    public RestExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
