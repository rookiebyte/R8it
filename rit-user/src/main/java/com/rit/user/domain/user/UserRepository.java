package com.rit.user.domain.user;


import java.util.Optional;

public interface UserRepository {

    Optional<UsersCredentials> fetchUsersCredentialsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User saveUser(User user);

    void updateUsersCredentials(User user, UsersCredentials credentials);
}
