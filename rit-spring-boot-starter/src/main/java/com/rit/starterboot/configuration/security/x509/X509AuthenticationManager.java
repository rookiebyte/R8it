package com.rit.starterboot.configuration.security.x509;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.function.Predicate;

public class X509AuthenticationManager implements AuthenticationManager {

    private final Predicate<String> principalPredicate;

    public X509AuthenticationManager(Predicate<String> principalPredicate) {
        this.principalPredicate = principalPredicate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof PreAuthenticatedAuthenticationToken token) {
            return authenticateToken(token);
        }
        return null;
    }

    private Authentication authenticateToken(PreAuthenticatedAuthenticationToken token) {
        var principal = token.getPrincipal();
        if (principal instanceof String principalAsString && principalPredicate.test(principalAsString)) {
            token.setAuthenticated(true);
        }
        return token;
    }
}
