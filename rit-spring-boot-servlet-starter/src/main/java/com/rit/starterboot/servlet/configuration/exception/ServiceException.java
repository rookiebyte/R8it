package com.rit.starterboot.servlet.configuration.exception;

import com.rit.robusta.util.Strings;

import java.util.UUID;

public class ServiceException extends RestRuntimeException {

    public static final int SERVER_ERROR_CODE = 500;
    private final String uuid;

    private ServiceException(RestExceptionCode restExceptionCode, String uuid, Throwable cause) {
        super(SERVER_ERROR_CODE, restExceptionCode, cause, uuid);
        this.uuid = uuid;
    }

    public ServiceException(RestExceptionCode restExceptionCode, Throwable cause) {
        this(restExceptionCode, fromCauseOrNewUUID(cause), cause);
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

    private static String fromCauseOrNewUUID(Throwable cause) {
        if (cause instanceof GenericFeignClientException genericFeignClientException) {
            var uuid = genericFeignClientException.getErrorAttributesResponse().uuid();
            if (Strings.isNotBlank(uuid)) {
                return uuid;
            }
        }
        return UUID.randomUUID().toString();
    }
}
