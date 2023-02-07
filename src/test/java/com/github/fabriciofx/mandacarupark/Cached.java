package com.github.fabriciofx.mandacarupark;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Cached<T> implements Supplier<T> {
    private final List<T> cache = new ArrayList<>(0);
    private final Supplier<T> supplier;

    public Cached(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (this.cache.size() == 0) {
            this.cache.add(this.supplier.get());
        }
        return this.cache.get(0);
    }
}
