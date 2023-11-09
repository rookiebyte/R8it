package com.rit.user.domain.user;

import com.google.common.base.Objects;
import com.rit.starterboot.domain.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public final class User {

    private final String id;
    private final String email;
    private final UserStatus userStatus;
    private final String username;
    private final String phoneNumber;
    private final Map<String, UserOtp> oneTimePasswords;

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
        oneTimePasswords.put(userOtp.getActionName(), userOtp);
    }
}
