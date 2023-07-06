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
package com.github.fabriciofx.mandacarupark.permanencia;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Permanencia;
import java.time.Duration;

public final class PermanenciaOf implements Permanencia {
    private final Duration duracao;

    public PermanenciaOf(final DataHora inicio, final DataHora termino) {
        this(Duration.between(inicio.dateTime(), termino.dateTime()));
    }

    public PermanenciaOf(final Duration duracao) {
        this.duracao = duracao;
    }

    @Override
    public int horas() {
        return this.duracao.toHoursPart();
    }

    @Override
    public int minutos() {
        return this.duracao.toMinutesPart();
    }

    @Override
    public Media sobre(final Media media) {
        return media.begin("permanencia")
            .with("horas", this.horas())
            .with("minutos", this.minutos())
            .end("permanencia");
    }
}
