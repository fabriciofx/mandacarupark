/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022-2023 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.mandacarupark.data;

import com.github.fabriciofx.mandacarupark.Data;
import java.util.HashMap;
import java.util.Map;

public final class DataMap implements Data {
    private final Map<String, Object> items;

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5),
                new MapEntryOf<>(key6, value6),
                new MapEntryOf<>(key7, value7),
                new MapEntryOf<>(key8, value8),
                new MapEntryOf<>(key9, value9),
                new MapEntryOf<>(key10, value10)
            )
        );
    }

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5),
                new MapEntryOf<>(key6, value6),
                new MapEntryOf<>(key7, value7),
                new MapEntryOf<>(key8, value8),
                new MapEntryOf<>(key9, value9)
            )
        );
    }

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5),
                new MapEntryOf<>(key6, value6),
                new MapEntryOf<>(key7, value7),
                new MapEntryOf<>(key8, value8)
            )
        );
    }

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5),
                new MapEntryOf<>(key6, value6),
                new MapEntryOf<>(key7, value7)
            )
        );
    }

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5),
                new MapEntryOf<>(key6, value6)
            )
        );
    }

    public DataMap(
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
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4),
                new MapEntryOf<>(key5, value5)
            )
        );
    }

    public DataMap(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3,
        final String key4,
        final Object value4
    ) {
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3),
                new MapEntryOf<>(key4, value4)
            )
        );
    }

    public DataMap(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2,
        final String key3,
        final Object value3
    ) {
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2),
                new MapEntryOf<>(key3, value3)
            )
        );
    }

    public DataMap(
        final String key1,
        final Object value1,
        final String key2,
        final Object value2
    ) {
        this(
            DataMap.of(
                new MapEntryOf<>(key1, value1),
                new MapEntryOf<>(key2, value2)
            )
        );
    }

    public DataMap(final String key1, final Object value1) {
        this(DataMap.of(new MapEntryOf<>(key1, value1)));
    }

    public DataMap(final Map<String, Object> items) {
        this.items = items;
    }

    @Override
    public <T> T get(final String query) {
        return (T) (this.items.get(query));
    }

    @Override
    public DataMap with(final String key, final Object value) {
        this.items.put(key, value);
        return new DataMap(this.items);
    }

    @Override
    public String toString() {
        return this.items.toString();
    }

    @SafeVarargs
    private static <X, Y> Map<X, Y> of(
        final Map.Entry<? extends X, ? extends Y> ...entries
    ) {
        final Map<X, Y> map = new HashMap<>(0);
        for (final Map.Entry<? extends X, ? extends Y> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
