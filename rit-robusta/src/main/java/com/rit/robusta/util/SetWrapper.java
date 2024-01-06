package com.rit.robusta.util;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetWrapper<E> {

    private final Set<E> value;

    SetWrapper(Set<E> value) {
        this.value = value;
    }

    public <K, V> Map<K, V> toMap(Function<E, K> keySupplier, Function<E, V> valueSupplier) {
        return value.stream().collect(Collectors.toMap(keySupplier, valueSupplier));
    }
}
