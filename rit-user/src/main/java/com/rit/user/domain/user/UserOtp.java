package com.rit.user.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;

@Builder
@Getter
public class UserOtp {

    private final OtpActionType actionType;
    private final LocalDateTime expiresAt;
    private final byte[] value;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean matches(byte[] value) {
        return Arrays.equals(this.value, value);
    }

    public String copyValueAsString() {
        return new String(value);
    }
}
