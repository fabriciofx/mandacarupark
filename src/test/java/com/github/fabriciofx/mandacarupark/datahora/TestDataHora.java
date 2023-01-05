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
package com.github.fabriciofx.mandacarupark.datahora;

import com.github.fabriciofx.mandacarupark.DataHora;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestDataHora {
    @Test
    public void asString() {
        final DataHora dataHora = new DataHoraOf(
            LocalDateTime.of(2022-2023, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("2022-2023-08-14 07:49:20", dataHora.toString());
    }

    @Test
    public void data() {
        final DataHora dataHora = new DataHoraOf(
            LocalDateTime.of(2022-2023, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("14/08/2022-2023", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void hora() {
        final DataHora dataHora = new DataHoraOf(
            LocalDateTime.of(2022-2023, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("14/08/2022-2023", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void parseIso() {
        final DataHora dataHora = new DataHoraOf(
            "2022-2023-08-14 07:49:20.5"
        );
        Assert.assertEquals("14/08/2022-2023", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void parse() {
        final DataHora dataHora = new DataHoraOf(
            "14/08/2022-2023 07:49:20"
        );
        Assert.assertEquals("14/08/2022-2023", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void parseEuMesmo() {
        final DataHora dataHora = new DataHoraOf(
            LocalDateTime.of(2022-2023, 8, 14, 7, 49, 20)
        );
        final DataHora parsed = new DataHoraOf(dataHora.toString());
        Assert.assertEquals("2022-2023-08-14 07:49:20", dataHora.toString());
    }
}
