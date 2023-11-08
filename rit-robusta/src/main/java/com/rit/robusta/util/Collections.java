package com.rit.robusta.util;

import java.util.Map;

public final class Collections {

    private Collections() {
    }

    public static boolean isEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> value) {
        return !isEmpty(value);
    }
}
