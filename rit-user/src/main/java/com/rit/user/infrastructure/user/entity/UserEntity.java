package com.rit.user.infrastructure.user.entity;

import com.google.common.base.Objects;
import com.rit.starterboot.domain.user.User;
import com.rit.user.domain.user.UsersCredentials;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    private String id;
    private String email;
    private String username;
    private String phoneNumber;
    private byte[] password;

    public UserEntity(User user, UsersCredentials credentials) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = credentials.password();
    }

    public User toDomain() {
        return User.builder().id(id).email(email).username(username).phoneNumber(phoneNumber).build();
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
