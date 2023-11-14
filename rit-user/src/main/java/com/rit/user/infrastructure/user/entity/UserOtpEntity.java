package com.rit.user.infrastructure.user.entity;

import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.UserOtp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserOtpEntity {

    private OtpActionType actionType;
    private LocalDateTime expiresAt;
    private byte[] value;

    public UserOtpEntity(UserOtp userOtp) {
        actionType = userOtp.getActionType();
        expiresAt = userOtp.getExpiresAt();
        value = userOtp.getValue();
    }

    public UserOtp toDomain() {
        return UserOtp.builder()
                      .actionType(actionType)
                      .expiresAt(expiresAt)
                      .value(value)
                      .build();
    }
}
