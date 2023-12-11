package com.rit.user.context.auth.dto;

import jakarta.validation.constraints.NotNull;

public record LoginResponse(

        @NotNull
        String jwt
) {
}
