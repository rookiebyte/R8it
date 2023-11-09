package com.rit.user.context.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @Email
        @NotEmpty
        String email,

        @NotEmpty
        String password
) {
}
