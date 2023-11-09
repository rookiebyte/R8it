package com.rit.starterboot.configuration.security.jwt;

import com.rit.starterboot.configuration.security.jwt.template.JwtTemplate;

public interface JwtEncoder {

    String encode(JwtTemplate template);
}
