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
package com.github.fabriciofx.mandacarupark.pagination.pages;

import com.github.fabriciofx.mandacarupark.pagination.Page;
import com.github.fabriciofx.mandacarupark.pagination.Pages;
import com.github.fabriciofx.mandacarupark.pagination.page.PageList;
import java.util.List;

public final class PagesList<T> implements Pages<T> {
    private final List<T> itens;
    private final int limit;

    public PagesList(final int limit, final List<T> itens) {
        this.itens = itens;
        this.limit = limit;
    }

    @Override
    public int count() {
        final int rem = Math.min(this.itens.size() % this.limit, 1);
        return this.itens.size() / this.limit + rem;
    }

    @Override
    public Page<T> page(final int number) {
        return new PageList<>(this.limit, number, this.itens);
    }
}
