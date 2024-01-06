package com.rit.user.infrastructure.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
public class TestEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    private String name;

    public TestEntity(UUID userId) {
        this.userId = userId;
        this.name = "ranom staff";
    }
}
