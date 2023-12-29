package com.rit.starterboot.servlet.configuration.security

import com.rit.robusta.util.Files
import com.rit.starterboot.servlet.RitSpringBootApplication
import com.rit.starterboot.servlet.configuration.SpringFullContextSpecification
import com.rit.starterboot.servlet.configuration.security.jwt.JwtEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared

import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

import static SecurityTestEndpoint.JWT_AUTH_NEEDED_GET
import static SecurityTestEndpoint.SIMPLE_GET
import static SecurityTestEndpoint.M_TLS_AUTH_NEEDED_GET
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.x509
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [SpringTestApp, SecurityTestEndpoint])
class SecurityConfigurationSpec extends SpringFullContextSpecification {

    @Autowired
    JwtEncoder jwtEncoder

    @Shared
    X509Certificate certificate

    def setupSpec() {
        certificate = loadCert("certs/test_internal.pem")
    }

    def 'get test endpoint, expect status is ok'() {
        when:
        def result = mockMvc.perform(get(SIMPLE_GET))
        then:
        result.andExpect(status().isOk())
    }

    def 'get jwt auth needed endpoint, without any authentication, expect status is unauthorized'() {
        when:
        def result = mockMvc.perform(get(JWT_AUTH_NEEDED_GET))
        then:
        result.andExpect(status().isUnauthorized())
    }

    def 'get mTls auth needed endpoint, without any authentication, expect status is unauthorized'() {
        when:
        def result = mockMvc.perform(get(M_TLS_AUTH_NEEDED_GET))
        then:
        result.andExpect(status().isUnauthorized())
    }

    def 'get jwt auth needed endpoint, with bearer token, expect status is ok'() {
        setup:
        def token = jwtEncoder.encode(new DummyJwtTemplate())
        when:
        def request = get(JWT_AUTH_NEEDED_GET)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isOk())
    }

    def 'get jwt auth needed endpoint, with mtls, expect status is unauthorized'() {
        when:
        def request = get(JWT_AUTH_NEEDED_GET).with(x509(certificate))
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isUnauthorized())
    }

    def 'get mtls auth needed endpoint, with bearer token, expect status is unauthorized'() {
        setup:
        def token = jwtEncoder.encode(new DummyJwtTemplate())
        when:
        def request = get(M_TLS_AUTH_NEEDED_GET)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isUnauthorized())
    }

    def 'get mtls auth needed endpoint, with mtls, expect status is ok'() {
        when:
        def request = get(M_TLS_AUTH_NEEDED_GET).with(x509(certificate))
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isOk())
    }


    @RitSpringBootApplication
    static class SpringTestApp {

        @Bean
        AuthenticationRequestMatcherProvider authenticationRequestMatcherProvider() {
            return new AuthenticationRequestMatcherProvider() {
                @Override
                protected RequestMatcher x509Matcher() {
                    return new AntPathRequestMatcher(M_TLS_AUTH_NEEDED_GET)
                }

                @Override
                protected List<String> permitAllPaths() {
                    return List.of(SIMPLE_GET)
                }
            }
        }
    }

    private static <T extends Certificate> T loadCert(String location) {
        try (var is = Files.getAsStream(location)) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509")
            return (T) certFactory.generateCertificate(is)
        }
        catch (Exception ex) {
            throw new IllegalArgumentException(ex)
        }
    }
}
