package com.rit.robusta.util;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapWrapper<K, V> {

    private final Map<K, V> value;

    MapWrapper(Map<K, V> value) {
        this.value = value;
    }

    public <T> Map<K, T> convertValues(Function<V, T> valueConverter) {
        return value.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, (e) -> valueConverter.apply(e.getValue())));
    }
}
