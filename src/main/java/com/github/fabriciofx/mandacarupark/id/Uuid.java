/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark.id;

import com.github.fabriciofx.mandacarupark.Id;
import java.util.Objects;
import java.util.UUID;

/**
 * Uuid.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.0.1
 */
public final class Uuid implements Id, Comparable<Uuid> {
    /**
     * O uuid.
     */
    private final String numero;

    /**
     * Ctor.
     */
    public Uuid() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Ctor.
     * @param numero Um número uuid.
     */
    public Uuid(final String numero) {
        this.numero = numero;
    }

    @Override
    public String numero() {
        return this.numero;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj instanceof Uuid
            && Uuid.class.cast(obj).numero.equals(this.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero);
    }

    @Override
    public String toString() {
        return this.numero;
    }

    @Override
    public int compareTo(final Uuid uuid) {
        return this.numero.compareTo(uuid.numero);
    }
}
