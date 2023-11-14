package com.rit.user.configuration.security;

import com.rit.starterboot.configuration.security.AuthenticationRequestMatcherProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthenticationRequestMatcherProvider extends AuthenticationRequestMatcherProvider {

    @Override
    protected List<String> permitAllPaths() {
        return List.of("/auth/**");
    }
}
