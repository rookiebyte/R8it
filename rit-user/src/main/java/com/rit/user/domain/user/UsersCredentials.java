package com.rit.user.domain.user;

public record UsersCredentials(String email, byte[] password) {

    public UsersCredentials(String email, String password) {
        this(email, password.getBytes());
    }

    public String passwordAsString() {
        return new String(password);
    }
}
