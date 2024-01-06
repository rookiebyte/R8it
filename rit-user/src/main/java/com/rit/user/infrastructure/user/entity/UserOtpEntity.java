package com.rit.user.infrastructure.user.entity;

import com.google.common.base.Objects;
import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.UserOtp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_otp_actions")
public class UserOtpEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private OtpActionType actionType;

    private LocalDateTime expiresAt;

    private byte[] value;

    public UserOtpEntity(UUID userId, UserOtp userOtp) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOtpEntity that = (UserOtpEntity) o;
        return Objects.equal(userId, that.userId) && actionType == that.actionType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, actionType);
    }
}
