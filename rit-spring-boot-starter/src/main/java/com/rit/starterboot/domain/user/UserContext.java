package com.rit.starterboot.domain.user;

public record UserContext(String rawJWT, String id) {

    public UserContext(String rawJWT, User user) {
        this(rawJWT, user.getId());
    }
}
