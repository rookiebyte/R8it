package com.rit.starterboot.configuration.exception;

public class ForbiddenException extends RestRuntimeException {

    public static final int FORBIDDEN_HTTP_CODE = 403;

    public ForbiddenException() {
        super(FORBIDDEN_HTTP_CODE, RestExceptionRepository.FORBIDDEN_EXCEPTION, null);
    }
}
