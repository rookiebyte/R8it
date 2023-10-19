package com.rit.starterboot.configuration.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class WithoutAuthenticationRequestMatcherProvider {

    protected List<String> pathMatchers() {
        return null;
    }

    public RequestMatcher[] requestMatchers() {
        var pathMatchers = Stream.concat(defaultPathMatchers(), Optional.ofNullable(pathMatchers()).stream().flatMap(Collection::stream));
        return pathMatchers.map(it -> (RequestMatcher) new AntPathRequestMatcher(it)).toArray(RequestMatcher[]::new);
    }

    private Stream<String> defaultPathMatchers() {
        return Stream.of(
                "/actuator/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**"
        );
    }
}
