package com.rit.starterboot.configuration.security.x509;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.function.Predicate;

public class ConfigurableX509AuthenticationFilter extends X509AuthenticationFilter {

    public ConfigurableX509AuthenticationFilter(String subjectPrincipalRegex, RequestMatcher requestMatcher, Predicate<String> principalPredicate) {
        super();
        setAuthenticationManager(new X509AuthenticationManager(principalPredicate));
        setRequiresAuthenticationRequestMatcher(requestMatcher);
        SubjectDnX509PrincipalExtractor principalExtractor = new SubjectDnX509PrincipalExtractor();
        principalExtractor.setSubjectDnRegex(subjectPrincipalRegex);
        setPrincipalExtractor(principalExtractor);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }
}
