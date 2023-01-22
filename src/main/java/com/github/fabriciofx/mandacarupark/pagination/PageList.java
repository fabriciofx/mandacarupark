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
package com.github.fabriciofx.mandacarupark.pagination;

import java.util.List;

public final class PageList<T> implements Page<T> {
    private final int limit;
    private final int position;
    private final List<T> list;

    public PageList(final int limit, final List<T> list) {
        this(limit, 0, list);
    }

    public PageList(final int limit, final int position, final List<T> list) {
        this.limit = limit;
        this.position = position;
        this.list = list;
    }

    @Override
    public List<T> content() {
        final int begin = this.position;
        final int end = Math.min(begin + this.limit, this.list.size());
        return this.list.subList(begin, end);
    }

    @Override
    public boolean hasNext() {
        return this.list.size() > this.position + this.limit;
    }

    @Override
    public Page<T> next() {
        final int pos = this.position + this.limit;
        return new PageList<T>(this.limit, pos, this.list);
    }

    @Override
    public boolean hasPrevious() {
        return this.position - this.limit >= 0;
    }

    @Override
    public Page<T> previous() {
        final int pos = this.position - this.limit;
        return new PageList<T>(this.limit, pos, this.list);
    }
}
