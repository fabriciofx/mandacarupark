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
package com.github.fabriciofx.mandacarupark.placa;

import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.text.Text;
import com.github.fabriciofx.mandacarupark.text.TextCached;
import java.util.Objects;
import java.util.Random;

public final class PlacaRandom implements Placa {
    final static char[] LETRAS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    final static char[] NUMEROS = {
        '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };
    final static char[] LETRAS_NUMEROS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2',
        '3', '4', '5', '6', '7', '8', '9', '0'
    };
    private final Text text;

    public PlacaRandom() {
        this.text = new TextCached(
            () -> {
                final StringBuilder sb = new StringBuilder();
                final Random rand = new Random();
                sb.append(
                    PlacaRandom.LETRAS[
                        rand.nextInt(PlacaRandom.LETRAS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.LETRAS[
                        rand.nextInt(PlacaRandom.LETRAS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.LETRAS[
                        rand.nextInt(PlacaRandom.LETRAS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.NUMEROS[
                        rand.nextInt(PlacaRandom.NUMEROS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.LETRAS_NUMEROS[
                        rand.nextInt(PlacaRandom.LETRAS_NUMEROS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.NUMEROS[
                        rand.nextInt(PlacaRandom.NUMEROS.length)
                    ]
                );
                sb.append(
                    PlacaRandom.NUMEROS[
                        rand.nextInt(PlacaRandom.NUMEROS.length)
                    ]
                );
                return sb.toString();
            }
        );
    }

    @Override
    public String numero() {
        return this.text.asString();
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj instanceof Placa &&
            Placa.class.cast(obj).numero().equals(this.numero()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero());
    }

    @Override
    public String toString() {
        return this.numero();
    }
}
