package com.rit.starterboot.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher;
    private final JwtDecoder jwtDecoder;
    private final BearerTokenResolver bearerTokenResolver;

    public BearerTokenAuthenticationFilter(RequestMatcher requestMatcher, JwtDecoder jwtDecoder) {
        super();
        this.requestMatcher = requestMatcher;
        this.jwtDecoder = jwtDecoder;
        this.bearerTokenResolver = new DefaultBearerTokenResolver();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!requestMatcher.matches(request)) {
            if (logger.isTraceEnabled()) {
                logger.trace(LogMessage.format("Did not authenticate since request did not match [%s]", this.requestMatcher));
            }
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.bearerTokenResolver.resolve(request);
        if (token.isEmpty()) {
            this.logger.trace("Did not process request since did not find bearer token");
            filterChain.doFilter(request, response);
            return;
        }
        var jwt = jwtDecoder.decode(token.get());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        var authToken = new JwtAuthenticationToken(jwt);
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
