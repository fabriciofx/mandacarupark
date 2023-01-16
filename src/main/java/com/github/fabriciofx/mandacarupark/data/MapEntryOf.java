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

import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.util.Map;

public class MapEntryOf<K,V> implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    public MapEntryOf(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(final V value) {
        throw new UnsupportedOperationException(
            "#setValue() is not supported"
        );
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Map.Entry
            && Map.Entry.class.cast(obj).getKey().equals(this.key)
            && Map.Entry.class.cast(obj).getValue().equals(this.value);
    }

    @Override
    public int hashCode() {
        int hash;
        if (this.key == null) {
            hash = 0;
        } else {
            hash = this.key.hashCode();
        }
        if (this.value == null) {
            hash ^= 1;
        } else {
            hash ^= this.value.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return new Sprintf("%s=%s", this.key, this.value).asString();
    }
}
