package com.rit.starterboot.configuration.exception;

final class RestExceptionRepository {

    public static final RestExceptionCode DEFAULT_VALIDATION_EXCEPTION = new RestExceptionCode("XXX-002",
            "Validation exception occur for fields: %s");
    public static final RestExceptionCode FORBIDDEN_EXCEPTION = new RestExceptionCode("XXX-001",
            "You don't have permission to do it! This situation will be reported");

    static final RestExceptionCode UNKNOWN_SERVICE_EXCEPTION = new RestExceptionCode("XXX-000",
            "Error occurred, contacting us provide %s");

    private RestExceptionRepository() {
    }
}
