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
package com.github.fabriciofx.mandacarupark;

import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraRandom;
import com.github.fabriciofx.mandacarupark.id.UuidRandom;
import com.github.fabriciofx.mandacarupark.placa.PlacaRandom;
import com.github.fabriciofx.mandacarupark.text.Sprintf;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class RandomDataForDatabase {
    public static void main(String[] args) {
        final int MAX = 50;
        final DateTimeFormatter format = DateTimeFormatter.ofPattern(
            "uuuu-MM-dd HH:mm:ss"
        );
        final Id[] ids = new Id[MAX];
        final Placa[] placas = new Placa[MAX];
        final DataHora[] dataHoras = new DataHora[MAX];
        for (int count = 0; count < MAX; count++) {
            ids[count] = new UuidRandom();
            placas[count] = new PlacaRandom();
            dataHoras[count] = new DataHoraRandom(
                new DataHoraOf("01/01/2023 00:00:00"),
                new DataHoraOf("26/07/2023 23:59:59")
            );
        }
        // Entradas
        System.out.println("----------- Entradas -------------");
        for (int count = 0; count < MAX; count++) {
            System.out.println(
                new Sprintf(
                    "(\n  '%s',\n  '%s',\n  '%s'\n),",
                    ids[count].numero(),
                    placas[count].numero(),
                    dataHoras[count].dateTime().format(format)
                ).asString()
            );
        }
        // Saídas
        System.out.println("----------- Saídas -------------");
        for (int count = 0; count < MAX; count++) {
            final long elapsed = Math.round((Math.floor(Math.random() * (12 * 60 - 60 + 1) + 60)));
            final LocalDateTime saida = dataHoras[count].dateTime().plusMinutes(elapsed);
            System.out.println(
                new Sprintf(
                    "(\n  '%s',\n  '%s',\n  '%s'\n),",
                    ids[count].numero(),
                    placas[count].numero(),
                    saida.format(format)
                ).asString()
            );
        }
    }
}
