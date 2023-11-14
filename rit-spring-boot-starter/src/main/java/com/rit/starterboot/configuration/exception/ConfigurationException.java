package com.rit.starterboot.configuration.exception;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(Exception ex) {
        super(ex);
    }
}
