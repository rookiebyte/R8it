package com.rit.user.infrastructure.user;

import com.rit.robusta.inmemory.InMemoryRepository;
import com.rit.robusta.util.Strings;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.domain.user.UsersCredentials;
import com.rit.user.infrastructure.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepository extends InMemoryRepository<String, UserEntity> implements UserRepository {

    public InMemoryUserRepository() {
        super(String.class, UserEntity.class);
    }

    @Override
    protected String provideKey(UserEntity user) {
        return user.getId();
    }

    @Override
    protected void overrideKey(UserEntity user, String key) {
        user.setId(key);
    }

    @Override
    public Optional<UsersCredentials> fetchUsersCredentialsByEmail(String email) {
        return findEntityByEmail(email).map(this::buildUserCredentials);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return findEntityByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> updateUser(User user) {
        return fetchUsersCredentialsByEmail(user.getEmail()).map(uc -> saveUser(user, uc));
    }

    @Override
    public Optional<User> findUserById(String id) {
        return findById(id).map(UserEntity::toDomain);
    }

    @Override
    public User saveUser(User user, UsersCredentials credentials) {
        var entity = new UserEntity(user);
        entity.setPassword(credentials.password());
        return save(entity).toDomain();
    }

    private UsersCredentials buildUserCredentials(UserEntity userEntity) {
        return new UsersCredentials(userEntity.getEmail(), userEntity.getPassword());
    }

    private Optional<UserEntity> findEntityByEmail(String email) {
        return findBy(it -> Strings.equal(email, it.getEmail()));
    }
}
