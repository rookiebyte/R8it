package com.rit.starterboot.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final BearerTokenResolver bearerTokenResolver;
    private final JwtDecoder jwtDecoder;

    public BearerTokenAuthenticationFilter(JwtDecoder jwtDecoder) {
        super();
        this.jwtDecoder = jwtDecoder;
        this.bearerTokenResolver = new DefaultBearerTokenResolver();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

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
