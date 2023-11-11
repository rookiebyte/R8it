package com.rit.starterboot.configuration.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AuthenticationRequestMatcherProvider {

    protected List<String> permitAllPaths() {
        return null;
    }

    protected RequestMatcher x509Matcher() {
        return request -> false;
    }

    public RequestMatcher jwtMatcher() {
        return new NegatedRequestMatcher(x509Matcher());
    }

    public final RequestMatcher[] permitAll() {
        var pathMatchers = Stream.concat(defaultPathMatchers(), Optional.ofNullable(permitAllPaths()).stream().flatMap(Collection::stream));
        return pathMatchers.map(it -> (RequestMatcher) new AntPathRequestMatcher(it)).toArray(RequestMatcher[]::new);
    }

    private static Stream<String> defaultPathMatchers() {
        return Stream.of(
                "/actuator/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**"
        );
    }
}
