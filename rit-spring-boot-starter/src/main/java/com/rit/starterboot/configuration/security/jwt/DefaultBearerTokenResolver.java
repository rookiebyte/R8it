package com.rit.starterboot.configuration.security.jwt;

import com.rit.starterboot.configuration.security.jwt.exception.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DefaultBearerTokenResolver implements BearerTokenResolver {

    private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    private static final String BEARER_TOKEN_HEADER_NAME = HttpHeaders.AUTHORIZATION;

    @Override
    public Optional<String> resolve(final HttpServletRequest request) {
        return resolveFromAuthorizationHeader(request);
    }

    private Optional<String> resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(BEARER_TOKEN_HEADER_NAME);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return Optional.empty();
        }
        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            throw new JwtException("Bearer token is malformed");
        }
        return Optional.ofNullable(matcher.group("token"));
    }
}
