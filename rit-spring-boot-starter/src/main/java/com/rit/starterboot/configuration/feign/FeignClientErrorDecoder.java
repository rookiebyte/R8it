package com.rit.starterboot.configuration.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rit.starterboot.configuration.exception.ErrorAttributesResponse;
import com.rit.starterboot.configuration.exception.GenericFeignClientException;
import com.rit.starterboot.configuration.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    FeignClientErrorDecoder() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            return tryDecode(methodKey, response);
        } catch (IOException ex) {
            return new ServiceException(ex);
        }
    }

    private Exception tryDecode(String methodKey, Response response) throws IOException {
        var errorAttributesResponse = readErrorAttributesResponse(response);
        return new GenericFeignClientException(genericMessage(methodKey, response), errorAttributesResponse);
    }

    private String genericMessage(String methodKey, Response response) {
        return "Exception occurred executing feign method %s. Response code: %d.".formatted(methodKey, response.status());
    }

    private ErrorAttributesResponse readErrorAttributesResponse(Response response) throws IOException {
        return objectMapper.readValue(response.body().asInputStream(), ErrorAttributesResponse.class);
    }
}
