package com.rit.user.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;

@Builder
@Getter
public class UserOtp {

    private final String actionName;
    private final LocalDateTime expiresAt;
    private final byte[] value;

    public String getActionName() {
        return actionName;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean matches(byte[] value) {
        return Arrays.equals(this.value, value);
    }
}
