package com.rit.user.domain.user;


import com.rit.starterboot.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<UsersCredentials> fetchUsersCredentialsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User saveUser(User user, UsersCredentials credentials);
}
