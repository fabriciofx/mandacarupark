package com.github.fabriciofx.mandacarupark;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomPort extends Number {
    private static final long serialVersionUID = -1544510274704233661L;
    private final Random random;
    private final int min;
    private final int max;

    public RandomPort() {
        this(1025, 65535);
    }

    public RandomPort(final int min, final int max) {
        this.random = new SecureRandom();
        this.min = min;
        this.max = max;
    }

    @Override
    public int intValue() {
        return this.random.nextInt(this.min, this.max);
    }

    @Override
    public long longValue() {
        throw new UnsupportedOperationException("#longValue");
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("#floatValue");
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("#doubleValue");
    }
}
