package com.rit.spock.testcontainers;

import org.testcontainers.containers.GenericContainer;

interface TestContainerProvider {

    GenericContainer provide();
}
