package com.rit.starterboot.configuration.security.jwt;

public interface Jwt {

    String getTokenValue();

    String getSubject();
}
