package com.rit.robusta.util;

import java.util.Map;
import java.util.Set;

public final class Collections {

    private Collections() {
    }

    public static boolean isEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmpty(Set<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> value) {
        return !isEmpty(value);
    }

    public static <K, V> MapWrapper<K, V> wrap(Map<K, V> value) {
        return new MapWrapper<>(value);
    }

    public static <E> SetWrapper<E> wrap(Set<E> value) {
        return new SetWrapper<>(value);
    }
}
