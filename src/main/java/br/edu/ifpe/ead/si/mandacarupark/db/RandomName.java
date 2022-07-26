package br.edu.ifpe.ead.si.mandacarupark.db;

import java.util.Random;

public class RandomName {
    private final int length;
    private final char[] chars;

    public RandomName() {
        this(5);
    }

    public RandomName(int length) {
        this(
            length,
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        );
    }

    public RandomName(int length, char ...chars) {
        this.length = length;
        this.chars = chars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(int idx = 0; idx < this.length; idx++) {
            int pos = rand.nextInt(this.chars.length);
            sb.append(this.chars[pos]);
        }
        return sb.toString();
    }
}
