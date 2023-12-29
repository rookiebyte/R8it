package com.rit.starterboot.servlet.configuration.exception;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(Exception ex) {
        super(ex);
    }
}
