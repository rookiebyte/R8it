package com.rit.starterboot.configuration.exception;

import java.util.UUID;

public class ServiceException extends RestRuntimeException {

    public static final int SERVER_ERROR_CODE = 500;
    private final String uuid;

    private ServiceException(RestExceptionCode restExceptionCode, String uuid, Throwable cause) {
        super(SERVER_ERROR_CODE, restExceptionCode, cause, uuid);
        this.uuid = uuid;
    }

    public ServiceException(RestExceptionCode restExceptionCode, Throwable cause) {
        this(restExceptionCode, UUID.randomUUID().toString(), cause);
    }

    public ServiceException(RestExceptionCode restExceptionCode) {
        this(restExceptionCode, UUID.randomUUID().toString(), null);
    }

    public ServiceException() {
        this(RestExceptionRepository.UNKNOWN_SERVICE_EXCEPTION, null);
    }

    public ServiceException(Throwable cause) {
        this(RestExceptionRepository.UNKNOWN_SERVICE_EXCEPTION, cause);
    }

    @Override
    public String getUuid() {
        return uuid;
    }
}
