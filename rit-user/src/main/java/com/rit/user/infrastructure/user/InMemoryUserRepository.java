package com.rit.user.infrastructure.user;

import com.rit.robusta.inmemory.InMemoryRepository;
import com.rit.robusta.util.Strings;
import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.domain.user.UsersCredentials;
import com.rit.user.infrastructure.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class InMemoryUserRepository extends InMemoryRepository<UUID, UserEntity> implements UserRepository {

    public InMemoryUserRepository() {
        super(UUID.class, UserEntity.class);
    }

    @Override
    protected UUID provideKey(UserEntity user) {
        return user.getId();
    }

    @Override
    protected void overrideKey(UserEntity user, UUID key) {
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
    public Optional<User> findUserById(UUID id) {
        return findById(id).map(UserEntity::toDomain);
    }

    @Override
    public List<User> findUserByIds(Set<UUID> friendsId) {
        return findAllBy(e -> friendsId.contains(e.getId())).stream().map(UserEntity::toDomain).toList();
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
