package com.rit.starterboot.servlet.configuration.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TestConstraintValidator implements ConstraintValidator<TestConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
