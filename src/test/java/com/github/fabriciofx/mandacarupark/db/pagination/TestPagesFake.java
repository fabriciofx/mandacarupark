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
package com.github.fabriciofx.mandacarupark.db.pagination;

import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.db.pagination.pages.PagesFake;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public final class TestPagesFake {
    @Test
    public void countLessThanLimit() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2)
        );
        Assert.assertEquals(1, pages.count());
    }

    @Test
    public void countGreaterThanLimit() {
        final Pages<Integer> pages1 = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6)
        );
        final Pages<Integer> pages2 = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7)
        );
        final Pages<Integer> pages3 = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
        );
        final Pages<Integer> pages4 = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
        );
        final Pages<Integer> pages5 = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        );
        Assert.assertEquals(2, pages1.count());
        Assert.assertEquals(3, pages2.count());
        Assert.assertEquals(3, pages3.count());
        Assert.assertEquals(3, pages4.count());
        Assert.assertEquals(4, pages5.count());
    }

    @Test
    public void contentByPage2() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
    }

    @Test
    public void contentByPage3() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());
    }

    @Test
    public void contentByPage4() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());

        Assert.assertEquals(4, pages.page(1).content().get(0).intValue());
    }

    @Test
    public void contentByPage5() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());

        Assert.assertEquals(4, pages.page(1).content().get(0).intValue());
        Assert.assertEquals(5, pages.page(1).content().get(1).intValue());
    }

    @Test
    public void contentByPage6() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());

        Assert.assertEquals(4, pages.page(1).content().get(0).intValue());
        Assert.assertEquals(5, pages.page(1).content().get(1).intValue());
        Assert.assertEquals(6, pages.page(1).content().get(2).intValue());
    }

    @Test
    public void contentByPage7() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());

        Assert.assertEquals(4, pages.page(1).content().get(0).intValue());
        Assert.assertEquals(5, pages.page(1).content().get(1).intValue());
        Assert.assertEquals(6, pages.page(1).content().get(2).intValue());

        Assert.assertEquals(7, pages.page(2).content().get(0).intValue());
    }

    @Test
    public void contentByPage8() {
        final Pages<Integer> pages = new PagesFake<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
        );
        Assert.assertEquals(1, pages.page(0).content().get(0).intValue());
        Assert.assertEquals(2, pages.page(0).content().get(1).intValue());
        Assert.assertEquals(3, pages.page(0).content().get(2).intValue());

        Assert.assertEquals(4, pages.page(1).content().get(0).intValue());
        Assert.assertEquals(5, pages.page(1).content().get(1).intValue());
        Assert.assertEquals(6, pages.page(1).content().get(2).intValue());

        Assert.assertEquals(7, pages.page(2).content().get(0).intValue());
        Assert.assertEquals(8, pages.page(2).content().get(1).intValue());
    }
}
