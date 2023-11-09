package com.rit.user.infrastructure.user.entity;

import com.rit.user.domain.user.UserOtp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserOtpEntity {

    private String actionName;
    private LocalDateTime expiresAt;
    private byte[] value;

    public UserOtpEntity(UserOtp userOtp) {
        actionName = userOtp.getActionName();
        expiresAt = userOtp.getExpiresAt();
        value = userOtp.getValue();
    }

    public UserOtp toDomain() {
        return UserOtp.builder()
                      .actionName(actionName)
                      .expiresAt(expiresAt)
                      .value(value)
                      .build();
    }
}
