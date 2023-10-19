package com.rit.user.factory

import com.rit.starterboot.configuration.jwt.properties.JwtProperties


trait PropertiesFactory {

    JwtProperties getJwtProperties() {
        new JwtProperties(
                '../jwt/keystore.jks',
                'password',
                'oauthkey',
                'password',
                24
        )
    }
}
