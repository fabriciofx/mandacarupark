package br.edu.ifpe.ead.si.mandacarupark.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRandomName {
    @Test
    public void nameWith5Chars() {
        String name = new RandomName().toString();
        Assertions.assertEquals(name.length(), 5);
    }
}
