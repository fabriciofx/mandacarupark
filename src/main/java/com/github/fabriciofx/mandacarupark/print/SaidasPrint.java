package com.github.fabriciofx.mandacarupark.print;

import com.github.fabriciofx.mandacarupark.Media;
import com.github.fabriciofx.mandacarupark.Printer;
import com.github.fabriciofx.mandacarupark.pagination.Page;

public final class SaidasPrint<T extends Printer> implements Printer {
    private final PagePrint<T> printer;

    public SaidasPrint(final Page<T> page) {
        this.printer = new PagePrint<>(
            "\\$\\{ss\\.entry}(\\s*.*\\s*.*\\s*.*\\s*.*\\s*.*\\s*)\\$\\{ss\\.end}",
            page
        );
    }

    @Override
    public Media print(final Media media) {
        return this.printer.print(media);
    }
}
