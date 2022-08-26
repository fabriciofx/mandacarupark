package com.github.fabriciofx.mandacarupark;

import java.util.Map;

public class Data {
    private final Map<String, Object> items;

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5,
        final String key6,
        final Object value6,
        final String key7,
        final Object value7,
        final String key8,
        final Object value8,
        final String key9,
        final Object value9,
        final String key10,
        final Object value10
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5,
            key6,
            value6,
            key7,
            value7,
            key8,
            value8,
            key9,
            value9,
            key10,
            value10
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5,
        final String key6,
        final Object value6,
        final String key7,
        final Object value7,
        final String key8,
        final Object value8,
        final String key9,
        final Object value9
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5,
            key6,
            value6,
            key7,
            value7,
            key8,
            value8,
            key9,
            value9
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5,
        final String key6,
        final Object value6,
        final String key7,
        final Object value7,
        final String key8,
        final Object value8
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5,
            key6,
            value6,
            key7,
            value7,
            key8,
            value8
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5,
        final String key6,
        final Object value6,
        final String key7,
        final Object value7
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5,
            key6,
            value6,
            key7,
            value7
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5,
        final String key6,
        final Object value6
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5,
            key6,
            value6
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4,
        final String key5,
        final Object value5
    ) {
        this(Map.of(
            key1,
            value1,
            key2,
            value2,
            key3,
            value3,
            key4,
            value4,
            key5,
            value5
        ));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4
    ) {
        this(Map.of(key1, value1, key2, value2, key3, value3, key4, value4));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3
    ) {
        this(Map.of(key1, value1, key2, value2, key3, value3));
    }

    public Data(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2
    ) {
        this(Map.of(key1, value1, key2, value2));
    }

    public Data(final String key1, final Object value1) {
        this(Map.of(key1, value1));
    }

    public Data(final Map<String, Object> items) {
        this.items = items;
    }

    public <T> T value(final String key) {
        return (T) (this.items.get(key));
    }

    public Data with(final String key, final Object value) {
        this.items.put(key, value);
        return new Data(this.items);
    }

    @Override
    public String toString() {
        return this.items.toString();
    }
}
