package com.rit.spock.testcontainers;

import com.rit.spock.testcontainers.exception.SpockTestContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.runtime.extension.IGlobalExtension;
import org.spockframework.runtime.model.SpecInfo;
import org.testcontainers.containers.GenericContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TestContainerGlobalExtension implements IGlobalExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestContainerGlobalExtension.class);
    private final ConcurrentHashMap<Class<? extends TestContainerProvider>, GenericContainer> containerMap;

    public TestContainerGlobalExtension() {
        containerMap = new ConcurrentHashMap();
    }

    @Override
    public void visitSpec(SpecInfo spec) {
        if (!spec.isSkipped()) {
            Optional.ofNullable(spec.getAnnotation(RunWithContainer.class)).ifPresent(i -> runContainer(i, spec));
        }
    }

    private void runContainer(RunWithContainer annotation, SpecInfo spec) {
        LOGGER.info("Visit spec {}", spec.getReflection().getSimpleName());
        var provider = annotation.value();
        if (!containerMap.contains(provider)) {
            var container = getInstance(provider);
            containerMap.put(provider, container);
            LOGGER.info("Starting new container: {}", container.getDockerImageName());
            container.start();
        }
    }

    private GenericContainer getInstance(Class<? extends TestContainerProvider> provider) {
        try {
            return provider.getConstructor().newInstance().provide();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new SpockTestContainerException(ex);
        }
    }

    @Override
    public void stop() {
        /*todo async*/
        for (var container : containerMap.values()) {
            LOGGER.info("Stoping container: {}", container.getDockerImageName());
            container.stop();
        }
    }
}
