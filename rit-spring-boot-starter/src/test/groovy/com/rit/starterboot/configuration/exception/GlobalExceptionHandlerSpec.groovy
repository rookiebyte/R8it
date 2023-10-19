package com.rit.starterboot.configuration.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.rit.robusta.util.Strings
import com.rit.starterboot.configuration.SpringFullContextSpecification
import org.spockframework.spring.SpringBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared

import static com.rit.starterboot.configuration.exception.GlobalExceptionHandlerTestEndpoint.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [SpringTestApp, GlobalExceptionHandlerTestEndpoint])
class GlobalExceptionHandlerSpec extends SpringFullContextSpecification {

    @Shared
    ObjectMapper objectMapper

    @SpringBean
    GlobalExceptionHandler globalExceptionHandler = Spy(GlobalExceptionHandler)

    def setupSpec() {
        objectMapper = new ObjectMapper()
    }

    def 'perform simple post without a body, expect status is unsupported media type'() {
        when:
        def result = mockMvc.perform(post(SIMPLE_POST_WITH_BODY))
        then:
        result.andExpect(status().isUnsupportedMediaType())
        1 * globalExceptionHandler.handleHttpMediaTypeException(_)
    }

    def 'perform simple get using post method, expect status is method not allowed'() {
        when:
        def result = mockMvc.perform(post(SIMPLE_GET))
        then:
        result.andExpect(status().isMethodNotAllowed())
        1 * globalExceptionHandler.handleHttpRequestMethodNotSupportedException(_)
    }

    def 'perform simple post with not valid body, expect status is bad request'() {
        when:
        def request = post(SIMPLE_POST_WITH_BODY)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content('''
                                {
                                    "emailConstraintField": "xxx",
                                    "unknownConstraintField": "aa"
                                }
                                ''')
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isBadRequest())
        1 * globalExceptionHandler.handleMethodArgumentNotValidException(_)
        def resultAsString = result.andReturn().response.getContentAsString()
        def error = objectMapper.readValue(resultAsString, ErrorAttributesResponse)
        error.code() == RestExceptionRepository.DEFAULT_VALIDATION_EXCEPTION.code
        error.fieldConstraintViolation()[SimpleValidatedRequest.NOT_EMPTY_FIELD] == ConstraintViolationType.NOT_EMPTY.name()
        error.fieldConstraintViolation()[SimpleValidatedRequest.NOT_NULL_FIELD] == ConstraintViolationType.NOT_NULL.name()
        error.fieldConstraintViolation()[SimpleValidatedRequest.EMAIL_CONSTRAINT_FIELD] == ConstraintViolationType.EMAIL_CONSTRAINT.name()
        error.fieldConstraintViolation()[SimpleValidatedRequest.UNKNOWN_CONSTRAINT_FIELD] == ConstraintViolationType.UNKNOWN.name()
    }

    def 'perform simple get with exception, expect status is internal server error'() {
        when:
        def result = mockMvc.perform(get(SIMPLE_GET_WITH_EXCEPTION))
        then:
        result.andExpect(status().isInternalServerError())
        1 * globalExceptionHandler.handleThrowable(_)
        1 * globalExceptionHandler.handleServiceException(_)
        def resultAsString = result.andReturn().response.getContentAsString()
        def error = objectMapper.readValue(resultAsString, ErrorAttributesResponse)
        error.code() == RestExceptionRepository.UNKNOWN_SERVICE_EXCEPTION.code
        Strings.isNotBlank(error.uuid())
    }

    @SpringBootApplication(exclude = [SecurityAutoConfiguration, ErrorMvcAutoConfiguration])
    static class SpringTestApp {
    }
}
