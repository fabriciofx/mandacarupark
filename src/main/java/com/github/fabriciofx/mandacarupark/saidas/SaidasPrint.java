package com.github.fabriciofx.mandacarupark.saidas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Print;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.pagination.Pages;
import com.github.fabriciofx.mandacarupark.saida.SaidaPrint;
import com.github.fabriciofx.mandacarupark.template.HtmlTemplate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaidasPrint implements Saidas, Print {
    private final Saidas saidas;

    public SaidasPrint(final Saidas saidas) {
        this.saidas = saidas;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        return this.saidas.saida(ticket, placa, dataHora);
    }

    @Override
    public List<Saida> procura(final Id id) {
        return this.saidas.procura(id);
    }

    @Override
    public Pages<Saida> pages(final int limit) {
        return this.saidas.pages(limit);
    }

    @Override
    public Template print(final Template template) {
        final String regex = "\\$\\{ss\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ss\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(template.toString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Saida saida : this.saidas.pages(30).page(0).content()) {
                Template page = new HtmlTemplate(matcher.group(1));
                page = new HtmlTemplate(new SaidaPrint(saida).print(page).toString());
                sb.append(new String(page.bytes()));
            }
        }
        return new HtmlTemplate(
            new String(template.bytes()).replaceAll(regex, sb.toString())
        );
    }
}
