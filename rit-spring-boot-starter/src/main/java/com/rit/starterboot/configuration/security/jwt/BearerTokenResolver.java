package com.rit.starterboot.configuration.security.jwt;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@FunctionalInterface
interface BearerTokenResolver {

    Optional<String> resolve(HttpServletRequest request);
}
