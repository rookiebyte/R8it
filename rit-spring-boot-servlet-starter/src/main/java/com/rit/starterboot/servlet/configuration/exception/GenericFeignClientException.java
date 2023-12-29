package com.rit.starterboot.servlet.configuration.exception;

public class GenericFeignClientException extends RuntimeException {

    private final ErrorAttributesResponse errorAttributesResponse;

    public GenericFeignClientException(String message, ErrorAttributesResponse errorAttributesResponse) {
        super(message);
        this.errorAttributesResponse = errorAttributesResponse;
    }

    public ErrorAttributesResponse getErrorAttributesResponse() {
        return errorAttributesResponse;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " attributes: {" + errorAttributesResponse + "}";
    }
}
