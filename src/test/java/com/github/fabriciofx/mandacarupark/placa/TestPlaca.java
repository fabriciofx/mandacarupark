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
package com.github.fabriciofx.mandacarupark.placa;

import com.github.fabriciofx.mandacarupark.Placa;
import org.junit.Assert;
import org.junit.Test;
import java.util.regex.Pattern;

public final class TestPlaca {
    @Test
    public void valida() {
        Assert.assertEquals("ABC1234", new PlacaOf("ABC1234").numero());
    }

    @Test
    public void mercosul() {
        Assert.assertEquals("ABC1K34", new PlacaOf("ABC1K34").numero());
    }

    @Test
    public void numeroNoNulls() {
        Assert.assertThrows(
            "NULL ao invés de um número de placa válido",
            IllegalStateException.class,
            () -> {
                new NoNulls(new PlacaOf(() -> null)).numero();
            }
        );
    }

    @Test
    public void placaNoNulls() {
        Assert.assertThrows(
            "NULL ao invés de uma placa válida",
            IllegalArgumentException.class,
            () -> {
                new NoNulls(null).numero();
            }
        );
    }

    @Test
    public void invalida() {
        Assert.assertThrows(
            "Placa: número inválido!",
            RuntimeException.class,
            () -> {
                new Restricao(new PlacaOf("ABC123J")).numero();
            }
        );
    }

    @Test
    public void random() {
        final Pattern padrao = Pattern.compile(
            "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}"
        );
        Assert.assertTrue(
            padrao.matcher(new PlacaRandom().numero()).matches()
        );
    }

    @Test
    public void equals() {
        Assert.assertTrue(
            new PlacaOf("ABC1234").equals(new PlacaOf("ABC1234"))
        );
    }

    @Test
    public void equalsRestricao() {
        Assert.assertTrue(
            new Restricao(new PlacaOf("ABC1234")).equals(new PlacaOf("ABC1234"))
        );
    }

    @Test
    public void equalsRandom() {
        final Placa random = new PlacaRandom();
        Assert.assertTrue(random.equals(new PlacaOf(random.numero())));
    }
}
