package com.rit.user.domain.user;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository {

    Optional<UsersCredentials> fetchUsersCredentialsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User saveUser(User user, UsersCredentials credentials);

    default Optional<User> updateUser(User user) {
        return fetchUsersCredentialsByEmail(user.getEmail()).map(uc -> saveUser(user, uc));
    }

    Optional<User> findUserById(UUID id);

    List<User> findUserByIds(Set<UUID> friendsId);
}
