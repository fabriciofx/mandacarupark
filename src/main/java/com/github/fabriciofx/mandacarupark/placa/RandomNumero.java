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

import com.github.fabriciofx.mandacarupark.text.Text;
import com.github.fabriciofx.mandacarupark.text.TextCached;
import java.util.Random;

public final class RandomNumero implements Text {
    final static char[] LETRAS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    final static char[] NUMEROS =
        {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    final static char[] LETRAS_NUMEROS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2',
        '3', '4', '5', '6', '7', '8', '9', '0'
    };
    private final Text name;

    public RandomNumero() {
        this.name = new TextCached(
            () -> {
                final StringBuilder sb = new StringBuilder();
                final Random rand = new Random();
                sb.append(this.LETRAS[rand.nextInt(this.LETRAS.length)]);
                sb.append(this.LETRAS[rand.nextInt(this.LETRAS.length)]);
                sb.append(this.LETRAS[rand.nextInt(this.LETRAS.length)]);
                sb.append(this.NUMEROS[rand.nextInt(this.NUMEROS.length)]);
                sb.append(this.LETRAS_NUMEROS[rand.nextInt(this.LETRAS_NUMEROS.length)]);
                sb.append(this.NUMEROS[rand.nextInt(this.NUMEROS.length)]);
                sb.append(this.NUMEROS[rand.nextInt(this.NUMEROS.length)]);
                return sb.toString();
            }
        );
    }

    @Override
    public String asString() {
        return this.name.asString();
    }
}
