package com.rit.notification

class SingleArgumentSpy<T> {

    private final Class<T> valueType
    private final int valueIndex
    private T value

    SingleArgumentSpy(Class<T> valueType, int valueIndex) {
        this.valueType = valueType
        this.valueIndex = valueIndex
    }

    SingleArgumentSpy(Class<T> valueType) {
        this(valueType, 0)
    }

    void spy(Object args) {
        value = getFromArgs(args, valueType, valueIndex)
    }

    T getValue() {
        return value
    }

    private static <T> T getFromArgs(Object args, Class<T> objectType, int index) {
        if (!args instanceof Object[]) {
            return null
        }
        def argsArray = (Object[]) args
        if (argsArray.length - 1 < index) {
            return null
        }
        def obj = argsArray[index]
        if (objectType.isInstance(obj)) {
            return objectType.cast(obj)
        }
        return null
    }
}
