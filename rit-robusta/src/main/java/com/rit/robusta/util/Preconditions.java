package com.rit.robusta.util;

import java.util.function.Predicate;

public final class Preconditions {

    private Preconditions() {
    }

    public static <T> T checkArgument(T value, Predicate<T> condition, String message) {
        if (!condition.test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
