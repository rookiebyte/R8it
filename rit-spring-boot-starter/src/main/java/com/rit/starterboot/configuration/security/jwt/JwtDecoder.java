package com.rit.starterboot.configuration.security.jwt;

public interface JwtDecoder {

    Jwt decode(String string);
}
