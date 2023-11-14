package com.rit.starterboot.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorAttributesResponse> handleThrowable(Throwable ex) {
        return handleServiceException(new ServiceException(ex));
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(HttpMediaTypeException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorAttributesResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var violationMap = ex.getFieldErrors().stream()
                             .collect(Collectors.toMap(this::getFieldPath, it -> getViolationType(it).name()));
        return handleRestRuntimeException(new ValidationException(violationMap));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorAttributesResponse> handleServiceException(ServiceException ex) {
        LOGGER.error("Unexpected error occurred with uuid[{}]. Cause:", ex.getUuid(), ex.getCause());
        var response = new ErrorAttributesResponse(ex);
        return ResponseEntity.status(response.expectedHttpCode()).body(response);
    }

    @ExceptionHandler(RestRuntimeException.class)
    public ResponseEntity<ErrorAttributesResponse> handleRestRuntimeException(RestRuntimeException ex) {
        var response = new ErrorAttributesResponse(ex);
        return ResponseEntity.status(response.expectedHttpCode()).body(response);
    }

    private String getFieldPath(FieldError fieldError) {
        return fieldError.getField();
    }

    private ConstraintViolationType getViolationType(FieldError fieldError) {
        if (fieldError == null || fieldError.getCode() == null) {
            return ConstraintViolationType.UNKNOWN;
        }
        return switch (fieldError.getCode()) {
            case "NotNull" -> ConstraintViolationType.NOT_NULL;
            case "NotEmpty" -> ConstraintViolationType.NOT_EMPTY;
            case "Email" -> ConstraintViolationType.EMAIL_CONSTRAINT;
            default -> {
                LOGGER.error("Unknown constraint violation code {} for field {}", fieldError.getCode(), fieldError.getField());
                yield ConstraintViolationType.UNKNOWN;
            }
        };
    }
}
