package com.github.fabriciofx.mandacarupark.text;

public final class SprintfOf implements Text {
    private final Text format;
    private final Text[] args;

    public SprintfOf(final String format, final Text... args) {
        this(() -> format, args);
    }

    public SprintfOf(final Text format, final Text... args) {
        this.format = format;
        this.args = args;
    }

    @Override
    public String asString() {
        final Object[] objs = new Object[this.args.length];
        for (int idx = 0; idx < this.args.length; idx++) {
            objs[idx] = this.args[idx].asString();
        }
        return String.format(this.format.asString(), objs);
    }
}
