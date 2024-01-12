package com.rit.spock.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresSqlContainerProvider implements TestContainerProvider {

    private static final String IMAGE = "postgres:15-alpine";

    @Override
    public GenericContainer provide() {
        return new SpringPostgresSqlContainer(DockerImageName.parse(IMAGE))
                .withPassword("password")
                .withUsername("username")
                .withDatabaseName("test");
    }
}
