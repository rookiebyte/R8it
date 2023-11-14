package com.rit.notification.configuration.security;

import com.rit.starterboot.configuration.security.AuthenticationRequestMatcherProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class NotificationAuthenticationRequestMatcherProvider extends AuthenticationRequestMatcherProvider {

    @Override
    protected RequestMatcher x509Matcher() {
        return new AntPathRequestMatcher("/**");
    }
}
