package com.rit.user.infrastructure.user.entity;

import com.google.common.base.Objects;
import com.rit.robusta.util.Collections;
import com.rit.starterboot.domain.user.UserStatus;
import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    private String id;
    private String email;
    private UserStatus userStatus;
    private String username;
    private String phoneNumber;
    private byte[] password;
    private Map<OtpActionType, UserOtpEntity> oneTimePasswords;

    public UserEntity(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userStatus = user.getUserStatus();
        this.oneTimePasswords = Collections.utilityWrapper(user.getOneTimePasswords()).convertValues(UserOtpEntity::new);
    }

    public User toDomain() {
        return User.builder()
                   .id(id)
                   .email(email)
                   .userStatus(userStatus)
                   .username(username)
                   .phoneNumber(phoneNumber)
                   .oneTimePasswords(Collections.utilityWrapper(oneTimePasswords).convertValues(UserOtpEntity::toDomain))
                   .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
