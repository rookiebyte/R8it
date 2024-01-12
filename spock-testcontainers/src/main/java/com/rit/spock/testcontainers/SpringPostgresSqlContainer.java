package com.rit.spock.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

class SpringPostgresSqlContainer<SELF extends SpringPostgresSqlContainer<SELF>> extends PostgreSQLContainer<SELF> {

    private static final String DATASOURCE_URL_PROPERTY = "spring.datasource.url";
    private static final String DATASOURCE_URL_USER = "spring.datasource.username";
    private static final String DATASOURCE_URL_PASSWORD = "spring.datasource.password";

    SpringPostgresSqlContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty(DATASOURCE_URL_PROPERTY, getJdbcUrl());
        System.setProperty(DATASOURCE_URL_USER, getUsername());
        System.setProperty(DATASOURCE_URL_PASSWORD, getPassword());
    }
}
