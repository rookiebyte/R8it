package com.rit.starterboot.servlet.domain.user;

import java.util.UUID;

public record UserContext(String rawJWT, UUID id) {
}
