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
package br.edu.ifpe.ead.si.mandacarupark;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public class TestPeriodo {
    @Test
    public void contemAgora() {
        LocalDateTime agora = LocalDateTime.now();
        Assert.assertTrue(new Periodo(agora, agora).contem(agora));
    }

    @Test
    public void contem() {
        LocalDateTime agora = LocalDateTime.now();
        Assert.assertTrue(
            new Periodo(agora, agora.plusMinutes(5)).contem(agora)
        );
    }

    @Test
    public void inicioAntesTermino() {
        Assert.assertThrows(
            "Periodo: o tempo de ínicio não pode ser depois do término",
            RuntimeException.class,
            () -> {
                LocalDateTime agora = LocalDateTime.now();
                new Periodo(agora.plusMinutes(1), agora).minutos();
            }
        );
    }
}
