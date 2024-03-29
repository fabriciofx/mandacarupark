package com.github.fabriciofx.mandacarupark.saidas;

import com.github.fabriciofx.mandacarupark.DataHora;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Placa;
import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Saida;
import com.github.fabriciofx.mandacarupark.Saidas;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.Ticket;
import com.github.fabriciofx.mandacarupark.db.pagination.Pages;
import com.github.fabriciofx.mandacarupark.saida.SaidaHtml;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaidasHtml implements Saidas, Html {
    private final Saidas origin;
    private final int limit;
    private final int number;
    private final Template template;

    public SaidasHtml(
        final Saidas saidas,
        final int limit,
        final int number,
        final Template template
    ) {
        this.origin = saidas;
        this.limit = limit;
        this.number = number;
        this.template = template;
    }

    @Override
    public Saida saida(
        final Ticket ticket,
        final Placa placa,
        final DataHora dataHora
    ) {
        return this.origin.saida(ticket, placa, dataHora);
    }

    @Override
    public List<Saida> procura(final Id id) {
        return this.origin.procura(id);
    }

    @Override
    public Pages<Saida> pages(final int limit) {
        return this.origin.pages(limit);
    }

    @Override
    public String html() {
        final String regex = "\\$\\{ss\\.begin}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ss\\.end}";
        final Pattern find = Pattern.compile(regex);
        final Matcher matcher = find.matcher(this.template.asString());
        final StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            for (final Saida saida : this.origin.pages(this.limit).page(this.number).content()) {
                Template page = this.template.create(matcher.group(1));
                page = this.template.create(new SaidaHtml(saida, page).html());
                sb.append(page.asString());
            }
        }
        return this.template.asString().replaceAll(
            regex,
            Matcher.quoteReplacement(sb.toString())
        );
    }
}
