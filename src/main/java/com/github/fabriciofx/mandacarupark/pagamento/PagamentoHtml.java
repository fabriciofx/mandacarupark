package com.github.fabriciofx.mandacarupark.pagamento;

import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.media.MapMedia;

public final class PagamentoHtml implements Pagamento, Html {
    private final Pagamento pagamento;
    private final Template template;

    public PagamentoHtml(final Pagamento pagamento, final Template template) {
        this.pagamento = pagamento;
        this.template = template;
    }

    @Override
    public Id id() {
        return this.pagamento.id();
    }

    @Override
    public Media sobre(final Media media) {
        return this.pagamento.sobre(media);
    }

    @Override
    public String html() {
        final Media media = this.sobre(new MapMedia());
        return this.template.with("id", media.select("id"))
            .with("dataHora", media.select("dataHora"))
            .with("valor", media.select("valor"))
            .asString();
    }
}
