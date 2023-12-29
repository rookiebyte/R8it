package com.rit.starterboot.servlet.configuration.security.jwt;

import com.rit.starterboot.servlet.configuration.security.jwt.template.JwtTemplate;

public interface JwtEncoder {

    String encode(JwtTemplate template);
}
