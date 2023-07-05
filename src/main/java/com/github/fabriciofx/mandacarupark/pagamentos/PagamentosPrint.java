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
package com.github.fabriciofx.mandacarupark.pagamentos;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Dinheiro;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Pagamentos;
import com.github.fabriciofx.mandacarupark.Print;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.pagamento.PagamentoPrint;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PagamentosPrint implements Pagamentos, Print {
    private final Pagamentos pagamentos;

    public PagamentosPrint(final Pagamentos pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Override
    public Pagamento pagamento(
        final Ticket ticket,
        final DataHora dataHora,
        final Dinheiro valor
    ) {
        return this.pagamentos.pagamento(ticket, dataHora, valor);
    }

    @Override
    public List<Pagamento> procura(final Id id) {
        return this.pagamentos.procura(id);
    }

    @Override
    public int quantidade() {
        return this.pagamentos.quantidade();
    }

    @Override
    public Iterator<Pagamento> iterator() {
        return this.pagamentos.iterator();
    }

    @Override
    public Media sobre(final Media media) {
        return this.pagamentos.sobre(media);
    }

    @Override
    public Template print(final Template template) {
        final String regex = "\\$\\{ps\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ps\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(template.toString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Pagamento pagamento : this.pagamentos) {
                Template page = template.from(matcher.group(1));
                page = template.from(new PagamentoPrint(pagamento).print(page).toString());
                sb.append(new String(page.bytes()));
            }
        }
        return template.from(
            new String(template.bytes()).replaceAll(regex, sb.toString())
        );
    }
}
