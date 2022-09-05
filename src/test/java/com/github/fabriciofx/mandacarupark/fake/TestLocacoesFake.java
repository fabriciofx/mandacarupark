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
package com.github.fabriciofx.mandacarupark.fake;

import com.github.fabriciofx.mandacarupark.Contas;
import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Estacionamento;
import com.github.fabriciofx.mandacarupark.Locacoes;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Periodo;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.conta.DomingoGratis;
import com.github.fabriciofx.mandacarupark.conta.Tolerancia;
import com.github.fabriciofx.mandacarupark.conta.ValorFixo;
import com.github.fabriciofx.mandacarupark.periodo.PeriodoOf;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestLocacoesFake {
    @Test
    public void locacoes() {
        final Entradas entradas = new EntradasFake();
        final Saidas saidas = new SaidasFake();
        final Pagamentos pagamentos = new PagamentosFake();
        final Estacionamento estacionamento = new EstacionamentoFake(
            entradas,
            saidas,
            pagamentos,
            new Contas(
                new DomingoGratis(),
                new Tolerancia(),
                new ValorFixo(new Dinheiro("5.00"))
            )
        );
        // Locação 1
        Placa placa = new Placa("ABC1234");
        final LocalDateTime dateTime = LocalDateTime.of(2022, 8, 2, 10, 30);
        Ticket ticket = estacionamento.entrada(placa, new DataHora(dateTime));
        ticket = estacionamento.pagamento(
            ticket,
            new DataHora(dateTime.plusMinutes(60))
        );
        estacionamento.saida(
            ticket,
            placa,
            new DataHora(dateTime.plusMinutes(70))
        );
        // Locação 2
        placa = new Placa("DEF5678");
        ticket = estacionamento.entrada(
            placa,
            new DataHora(dateTime.plusMinutes(1))
        );
        ticket = estacionamento.pagamento(
            ticket,
            new DataHora(dateTime.plusMinutes(40))
        );
        estacionamento.saida(
            ticket,
            placa,
            new DataHora(dateTime.plusMinutes(45))
        );
        // Locação 3
        placa = new Placa("GHI9012");
        ticket = estacionamento.entrada(
            placa,
            new DataHora(dateTime.plusMinutes(2))
        );
        ticket = estacionamento.pagamento(
            ticket,
            new DataHora(dateTime.plusMinutes(55))
        );
        estacionamento.saida(
            ticket,
            placa,
            new DataHora(dateTime.plusMinutes(52))
        );
        final Locacoes locacoes = new LocacoesFake(
            entradas,
            saidas,
            pagamentos,
            new PeriodoOf(dateTime.minusMinutes(5), dateTime.plusMinutes(80))
        );
    }
}
