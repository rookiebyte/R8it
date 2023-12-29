package com.rit.starterboot.servlet.configuration.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SimpleValidatedRequest(

        @NotEmpty
        String notEmptyField,

        @NotNull
        Integer notNullField,

        @Email
        String emailConstraintField,

        @TestConstraint
        String unknownConstraintField
) {

    static final String NOT_EMPTY_FIELD = "notEmptyField";
    static final String NOT_NULL_FIELD = "notNullField";
    static final String EMAIL_CONSTRAINT_FIELD = "emailConstraintField";
    static final String UNKNOWN_CONSTRAINT_FIELD = "unknownConstraintField";
}
