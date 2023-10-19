package com.rit.user.configuration.security;

import com.rit.starterboot.configuration.security.WithoutAuthenticationRequestMatcherProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWithoutAuthenticationRequestMatcherProvider extends WithoutAuthenticationRequestMatcherProvider {

    @Override
    protected List<String> pathMatchers() {
        return List.of("/auth/**");
    }
}
