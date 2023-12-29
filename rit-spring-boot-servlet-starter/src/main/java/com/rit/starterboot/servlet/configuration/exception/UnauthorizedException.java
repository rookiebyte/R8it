package com.rit.starterboot.servlet.configuration.exception;

public class UnauthorizedException extends RestRuntimeException {

    public static final int UNAUTHORIZED_HTTP_CODE = 401;

    public UnauthorizedException(RestExceptionCode restExceptionCode) {
        super(UNAUTHORIZED_HTTP_CODE, restExceptionCode, null);
    }
}
