package com.github.fabriciofx.mandacarupark.pagamento;

import com.github.fabriciofx.mandacarupark.Id;
import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Pagamento;
import com.github.fabriciofx.mandacarupark.Print;
import com.github.fabriciofx.mandacarupark.Template;
import com.github.fabriciofx.mandacarupark.media.MapMedia;

public final class PagamentoPrint implements Pagamento, Print {
    private final Pagamento pagamento;

    public PagamentoPrint(final Pagamento pagamento) {
        this.pagamento = pagamento;
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
    public Template print(final Template template) {
        final Media media = this.sobre(new MapMedia());
        return template.with("id", media.select("id"))
            .with("dataHora", media.select("dataHora"))
            .with("valor", media.select("valor"));
    }
}
