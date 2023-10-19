package com.rit.starterboot.configuration.exception;

import java.util.Map;

class RestRuntimeException extends RuntimeException {

    private final int expectedHttpCode;
    private final String code;

    RestRuntimeException(int expectedHttpCode, RestExceptionCode restExceptionCode, Throwable cause, Object... messageParams) {
        super(restExceptionCode.getMessage().formatted(messageParams), cause);
        this.expectedHttpCode = expectedHttpCode;
        this.code = restExceptionCode.getCode();
    }

    public int getExpectedHttpCode() {
        return expectedHttpCode;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> getFieldConstraintViolation() {
        return null;
    }

    public String getUuid() {
        return null;
    }
}
