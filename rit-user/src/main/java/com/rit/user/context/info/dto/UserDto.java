package com.rit.user.context.info.dto;

import com.rit.user.domain.user.User;
import jakarta.validation.constraints.NotNull;

public record UserDto(

        @NotNull
        String id,

        @NotNull
        String email,

        @NotNull
        String username
) {

    public UserDto(User user) {
        this(user.getId().toString(), user.getEmail(), user.getUsername());
    }
}
