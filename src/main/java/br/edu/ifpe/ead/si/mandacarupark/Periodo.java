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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Calcula o tempo em minutos decorridos entre o início e o término.
 */
public final class Periodo {
    private final LocalDateTime inicio;
    private final LocalDateTime termino;

    public Periodo(final LocalDateTime inicio, final LocalDateTime termino) {
        this.inicio = inicio;
        this.termino = termino;
    }

    public long minutos() {
        Duration decorrido = Duration.between(this.inicio, this.termino);
        return decorrido.toMinutes();
    }

    public boolean contem(final LocalDateTime dataHora) {
        return this.inicio.compareTo(dataHora) <= 0 &&
            this.termino.compareTo(dataHora) >= 0;
    }

    public String inicio() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss"
        );
        return this.inicio.format(formato);
    }

    public String termino() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss"
        );
        return this.termino.format(formato);
    }
}
