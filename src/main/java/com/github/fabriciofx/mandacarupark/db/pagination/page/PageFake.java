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
package com.github.fabriciofx.mandacarupark.db.pagination.page;

import com.github.fabriciofx.mandacarupark.db.pagination.Page;
import java.util.List;

public final class PageFake<T> implements Page<T> {
    private final int limit;
    private final int number;
    private final List<T> itens;

    public PageFake(
        final int limit,
        final List<T> itens
    ) {
        this(limit, 0, itens);
    }

    public PageFake(
        final int limit,
        final int number,
        final List<T> itens
    ) {
        this.limit = limit;
        this.number = number;
        this.itens = itens;
    }

    @Override
    public List<T> content() {
        final int begin = this.number * this.limit;
        final int end = Math.min(begin + this.limit, this.itens.size());
        return this.itens.subList(begin, end);
    }

    @Override
    public boolean hasNext() {
        final int rem = Math.min(this.itens.size() % this.limit, 1);
        final int pages = this.itens.size() / this.limit + rem;
        return this.number < pages - 1;
    }

    @Override
    public Page<T> next() {
        return new PageFake<>(
            this.limit,
            this.number + 1,
            this.itens
        );
    }

    @Override
    public boolean hasPrevious() {
        return this.number > 0;
    }

    @Override
    public Page<T> previous() {
        return new PageFake<>(
            this.limit,
            this.number - 1,
            this.itens
        );
    }
}
