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

import com.github.fabriciofx.mandacarupark.text.Sprintf;
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
    public int count() {
        int cnt = this.list.size() - this.position;
        if (cnt > this.limit) {
            cnt = this.limit;
        }
        return cnt;
    }

    @Override
    public T content(final int index) {
        final int start = this.position;
        final int end  = start + this.limit - 1;
        final int pos = start + index;
        if (pos < start || pos > end) {
            throw new IndexOutOfBoundsException(
                new Sprintf(
                    "index must be between %d and %d",
                    start,
                    end
                ).asString()
            );
        }
        return this.list.get(pos);
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
    public boolean hasPrev() {
        return this.position - this.limit >= 0;
    }

    @Override
    public Page<T> prev() {
        final int pos = this.position - this.limit;
        return new PageList<T>(this.limit, pos, this.list);
    }
}
