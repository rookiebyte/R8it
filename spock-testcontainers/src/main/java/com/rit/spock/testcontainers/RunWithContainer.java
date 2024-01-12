package com.rit.spock.testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RunWithContainer {

    /*todo change to array*/
    Class<? extends TestContainerProvider> value();
}
