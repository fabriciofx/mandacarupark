package com.github.fabriciofx.mandacarupark.pagamento;

import com.github.fabriciofx.mandacarupark.Html;
import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.media.MemMedia;

public final class PagamentoHtml implements Pagamento, Html {
    private final Pagamento origin;
    private final Template template;

    public PagamentoHtml(final Pagamento pagamento, final Template template) {
        this.origin = pagamento;
        this.template = template;
    }

    @Override
    public Id id() {
        return this.origin.id();
    }

    @Override
    public Media sobre(final Media media) {
        return this.origin.sobre(media);
    }

    @Override
    public String html() {
        final Media media = this.sobre(new MemMedia());
        return this.template.with("id", media.select("id"))
            .with("dataHora", media.select("dataHora"))
            .with("valor", media.select("valor"))
            .asString();
    }
}
