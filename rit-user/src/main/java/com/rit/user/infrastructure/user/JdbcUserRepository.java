package com.rit.user.infrastructure.user;

import com.rit.user.infrastructure.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JdbcUserRepository extends CrudRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
}
