package com.rit.user.infrastructure;

import com.rit.user.domain.user.User;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.domain.user.UsersCredentials;
import com.rit.user.infrastructure.user.JdbcUserRepository;
import com.rit.user.infrastructure.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JdbcUserFacadeRepository implements UserRepository {

    private final JdbcUserRepository jdbcUserRepository;

    @Override
    public Optional<UsersCredentials> fetchUsersCredentialsByEmail(String email) {
        return jdbcUserRepository.findOne(whereEmail(email)).map(entity -> new UsersCredentials(entity.getEmail(), entity.getPassword()));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return jdbcUserRepository.findOne(whereEmail(email)).map(UserEntity::toDomain);
    }

    @Override
    public User saveUser(User user, UsersCredentials credentials) {
        var entity = new UserEntity(user);
        entity.setPassword(credentials.password());
        return jdbcUserRepository.save(entity).toDomain();
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return jdbcUserRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public List<User> findUserByIds(Set<UUID> friendsId) {
        return jdbcUserRepository.findAll(whereIdIn(friendsId)).stream().map(UserEntity::toDomain).toList();
    }

    private Specification<UserEntity> whereIdIn(Set<UUID> friendsId) {
        return (root, query, builder) -> builder.in(root.get("id").in(friendsId));
    }

    private Specification<UserEntity> whereEmail(String email) {
        return (root, query, builder) -> builder.equal(root.get("email"), email);
    }
}
