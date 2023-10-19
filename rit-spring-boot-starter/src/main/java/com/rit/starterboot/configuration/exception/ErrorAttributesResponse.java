package com.rit.starterboot.configuration.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorAttributesResponse(

        @JsonIgnore
        int expectedHttpCode,
        String code,
        String message,
        Map<?, ?> fieldConstraintViolation,
        String uuid
) {


    ErrorAttributesResponse(RestRuntimeException ex) {
        this(ex.getExpectedHttpCode(), ex.getCode(), ex.getMessage(), ex.getFieldConstraintViolation(), ex.getUuid());
    }
}
