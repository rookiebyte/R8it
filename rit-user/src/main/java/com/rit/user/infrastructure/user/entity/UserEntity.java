package com.rit.user.infrastructure.user.entity;

import com.google.common.base.Objects;
import com.rit.robusta.util.Collections;
import com.rit.starterboot.servlet.domain.user.UserStatus;
import com.rit.user.domain.user.OtpActionType;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserOtp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    private String email;

    private UserStatus userStatus;

    private String username;

    private byte[] password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId")
    private Set<UserOtpEntity> oneTimePasswords;


    public UserEntity(User user) {
        id = Optional.ofNullable(user.getId()).orElseGet(UUID::randomUUID);
        email = user.getEmail();
        username = user.getUsername();
        userStatus = user.getUserStatus();
        oneTimePasswords = Collections.wrap(user.getOneTimePasswords()).toSet(v -> new UserOtpEntity(id, v));
    }

    public User toDomain() {
        return User.builder()
                   .id(id)
                   .email(email)
                   .userStatus(userStatus)
                   .username(username)
                   .oneTimePasswords(oneTimePasswordsAsMap())
                   .build();
    }

    private Map<OtpActionType, UserOtp> oneTimePasswordsAsMap() {
        return Collections.wrap(oneTimePasswords).toMap(UserOtpEntity::getActionType, UserOtpEntity::toDomain);
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
