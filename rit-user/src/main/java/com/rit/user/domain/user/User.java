package com.rit.user.domain.user;

import com.google.common.base.Objects;
import com.rit.starterboot.servlet.domain.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public final class User {

    private final UUID id;
    private final String email;
    private final String username;
    private UserStatus userStatus;
    private final Map<OtpActionType, UserOtp> oneTimePasswords;

    @Builder
    private User(UUID id, String email, String username, UserStatus userStatus, Map<OtpActionType, UserOtp> oneTimePasswords) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userStatus = userStatus;
        this.oneTimePasswords = oneTimePasswords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void addOtp(UserOtp userOtp) {
        /*todo: remove expired ones*/
        oneTimePasswords.put(userOtp.getActionType(), userOtp);
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
