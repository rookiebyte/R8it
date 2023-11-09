package com.rit.starterboot.configuration.security

import com.rit.starterboot.configuration.security.jwt.template.JwtTemplate

import java.time.Instant
import java.time.temporal.ChronoUnit

class DummyJwtTemplate extends JwtTemplate {

    @Override
    String getSubject() {
        return "test subject"
    }

    @Override
    Instant getExpiresAt() {
        return Instant.now().plus(1, ChronoUnit.DAYS)
    }
}
