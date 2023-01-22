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

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public final class TestPageList {
    @Test
    public void countLessThanLimit() {
        final Page<Integer> page = new PageList<>(
            3,
            Arrays.asList(1, 2)
        );
        Assert.assertEquals(2, page.content().size());
    }

    @Test
    public void countGreaterThanLimit() {
        final Page<Integer> page = new PageList<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7)
        );
        Assert.assertEquals(3, page.content().size());
        Assert.assertEquals(3, page.next().content().size());
        Assert.assertEquals(1, page.next().next().content().size());
    }

    @Test
    public void contentNext() {
        final Page<Integer> page = new PageList<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7)
        );
        Assert.assertEquals(1, page.content().get(0).intValue());
        Assert.assertEquals(2, page.content().get(1).intValue());
        Assert.assertEquals(3, page.content().get(2).intValue());
        Assert.assertEquals(4, page.next().content().get(0).intValue());
        Assert.assertEquals(5, page.next().content().get(1).intValue());
        Assert.assertEquals(6, page.next().content().get(2).intValue());
        Assert.assertEquals(7, page.next().next().content().get(0).intValue());
    }

    @Test
    public void contentIndexOutOfBoundPositive() {
        Assert.assertThrows(
            "index must be between 0 and 2",
            IndexOutOfBoundsException.class,
            () -> {
                new PageList<>(
                    2,
                    Arrays.asList(1, 2, 3)
                ).content().get(2);
            }
        );
    }


    @Test
    public void contentIndexOutOfBoundNegative() {
        Assert.assertThrows(
            "index must be between 0 and 2",
            IndexOutOfBoundsException.class,
            () -> {
                new PageList<>(
                    2,
                    Arrays.asList(1, 2, 3)
                ).content().get(-1);
            }
        );
    }

    @Test
    public void hasNextOneElement() {
        final Page<Integer> page = new PageList<>(
            2,
            Arrays.asList(1)
        );
        Assert.assertFalse(page.hasNext());
    }

    @Test
    public void hasNext() {
        final Page<Integer> page = new PageList<>(
            2,
            Arrays.asList(1, 2, 3)
        );
        Assert.assertTrue(page.hasNext());
        Assert.assertFalse(page.next().hasNext());
    }

    @Test
    public void noHasPreviousForTheFirstPage() {
        final Page<Integer> page = new PageList<>(
            2,
            Arrays.asList(1, 2, 3)
        );
        Assert.assertFalse(page.hasPrevious());
    }

    @Test
    public void hasPrevious() {
        Page<Integer> page = new PageList<>(
            2,
            Arrays.asList(1, 2, 3)
        );
        page = page.next();
        Assert.assertTrue(page.hasPrevious());
    }

    @Test
    public void contentPrevious() {
        Page<Integer> page = new PageList<>(
            3,
            Arrays.asList(1, 2, 3, 4, 5, 6, 7)
        );
        page = page.next().next();
        Assert.assertEquals(7, page.content().get(0).intValue());
        Assert.assertEquals(4, page.previous().content().get(0).intValue());
        Assert.assertEquals(5, page.previous().content().get(1).intValue());
        Assert.assertEquals(6, page.previous().content().get(2).intValue());
        Assert.assertEquals(1, page.previous().previous().content().get(0).intValue());
        Assert.assertEquals(2, page.previous().previous().content().get(1).intValue());
        Assert.assertEquals(3, page.previous().previous().content().get(2).intValue());
    }
}
