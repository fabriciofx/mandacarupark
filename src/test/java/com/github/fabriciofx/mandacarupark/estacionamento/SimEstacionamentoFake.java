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
package com.github.fabriciofx.mandacarupark.estacionamento;

import com.github.fabriciofx.mandacarupark.Regras;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.id.Uuid;
import com.github.fabriciofx.mandacarupark.regra.DomingoGratis;
import com.github.fabriciofx.mandacarupark.regra.Tolerancia;
import com.github.fabriciofx.mandacarupark.regra.ValorFixo;
import com.github.fabriciofx.mandacarupark.regras.RegrasOf;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraOf;
import com.github.fabriciofx.mandacarupark.datahora.DataHoraRandom;
import com.github.fabriciofx.mandacarupark.dinheiro.DinheiroOf;
import com.github.fabriciofx.mandacarupark.entradas.EntradasFake;
import com.github.fabriciofx.mandacarupark.pagamentos.PagamentosFake;
import com.github.fabriciofx.mandacarupark.placa.PlacaRandom;
import com.github.fabriciofx.mandacarupark.saidas.SaidasFake;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public final class SimEstacionamentoFake {
    @Test
    public void simulacao() {
        // 1. Monta-se o ambiente do estacionamento
        final Regras regras = new RegrasOf(
            new DomingoGratis(),
            new Tolerancia(),
            new ValorFixo(new DinheiroOf("5.00"))
        );
        final Pagamentos pagamentos = new PagamentosFake();
        final Entradas entradas = new EntradasFake(pagamentos);
        final Saidas saidas = new SaidasFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            regras
        );
        for (int idx = 0; idx < 100; idx++) {
            final Placa placa = new PlacaRandom();
            final DataHora dataHora = new DataHoraRandom(
                LocalDateTime.of(2022, 8, 2, 8, 0, 0),
                LocalDateTime.of(2022, 8, 2, 22, 0, 0)
            );
            // 2. Um veículo aleatório, de um tempo aleatório T dá entrada no
            //    estacionamento e recebe um ticket
            final Ticket ticket = estacionamento.entrada(
                new Uuid(),
                placa,
                dataHora
            );
            // 3. O proprietário do veículo guarda o ticket
            //new ImagemParaArquivo(ticket.imagem(), "ticket.png").salva();
            // 4. Depois de um tempo aletório T + N, o proprietário paga o
            //    estacionamento
            final int N = ThreadLocalRandom.current().nextInt(10, 841);
            estacionamento.pagamento(
                ticket,
                new DataHoraOf(dataHora.dateTime().plusMinutes(N))
            );
            // 5. Depois de um tempo aletório T + N + K, o proprietário deixa o
            //    estacionamento
            final int K = ThreadLocalRandom.current().nextInt(N, 841);
            estacionamento.saida(
                ticket,
                placa,
                new DataHoraOf(dataHora.dateTime().plusMinutes(N + K))
            );
        }
        //new File("ticket.png").delete();
    }
}
