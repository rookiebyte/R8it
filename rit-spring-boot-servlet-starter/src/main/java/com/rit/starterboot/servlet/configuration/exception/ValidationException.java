package com.rit.starterboot.servlet.configuration.exception;

import java.util.Map;

public class ValidationException extends RestRuntimeException {

    private static final int BAD_REQUEST_CODE = 400;
    private final Map<String, String> fieldConstraintViolation;

    public ValidationException(Map<String, String> fieldConstraintViolation) {
        super(BAD_REQUEST_CODE, RestExceptionRepository.DEFAULT_VALIDATION_EXCEPTION, null,
                String.join(", ", fieldConstraintViolation.keySet()));
        this.fieldConstraintViolation = fieldConstraintViolation;
    }

    public ValidationException(RestExceptionCode restExceptionCode, Object... messageParams) {
        super(BAD_REQUEST_CODE, restExceptionCode, null, messageParams);
        fieldConstraintViolation = null;
    }

    @Override
    public Map<String, String> getFieldConstraintViolation() {
        return fieldConstraintViolation;
    }
}
