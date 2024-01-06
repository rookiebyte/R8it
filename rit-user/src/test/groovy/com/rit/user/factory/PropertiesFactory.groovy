package com.rit.user.factory

import com.rit.starterboot.servlet.configuration.jwt.properties.JwtProperties
import com.rit.user.configuration.otp.properties.OtpProperties


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

    OtpProperties getOtpProperties() {
        new OtpProperties("3132333435363738393031323334353637383930", 6, 1)
    }
}
