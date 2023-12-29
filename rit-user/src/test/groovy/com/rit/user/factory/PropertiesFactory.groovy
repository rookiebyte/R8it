package com.rit.user.factory

import com.rit.starterboot.servlet.configuration.jwt.properties.JwtProperties


trait PropertiesFactory {

    JwtProperties getJwtProperties() {
        def encoder = new JwtProperties.EncoderProperties(true, 24)
        new JwtProperties(
                '../jwt/keystore.jks',
                'password',
                'oauthkey',
                'password',
                encoder,
        )
    }
}
