package com.rit.starterboot.servlet.configuration.security.jwt;

public interface JwtDecoder {

    Jwt decode(String string);
}
