package com.rit.user.context.auth.dto;

import com.rit.user.domain.user.LoginCredentials;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @Email
        @NotEmpty
        String email,

        @NotEmpty
        String password
) implements LoginCredentials {

    @Override
    public String getUniqueIdentifier() {
        return email();
    }

    @Override
    public String getPassword() {
        return password();
    }
}
