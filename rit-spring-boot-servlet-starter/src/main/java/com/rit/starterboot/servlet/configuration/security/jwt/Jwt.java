package com.rit.starterboot.servlet.configuration.security.jwt;

public interface Jwt {

    String getTokenValue();

    String getSubject();
}
