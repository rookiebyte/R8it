package com.rit.starterboot.domain.user;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class User {

    private final String id;
    private final String email;
    private final String username;
    private final String phoneNumber;

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
}
