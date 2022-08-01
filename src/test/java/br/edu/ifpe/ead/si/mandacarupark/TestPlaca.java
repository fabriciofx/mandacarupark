package br.edu.ifpe.ead.si.mandacarupark;

import org.junit.Assert;
import org.junit.Test;

public class TestPlaca {
    @Test
    public void valida() {
        Assert.assertEquals(new Placa("ABC1234").toString(), "ABC1234");
    }

    @Test
    public void mercosul() {
        Assert.assertEquals(new Placa("ABC1K34").toString(), "ABC1K34");
    }

    @Test
    public void invalida() {
        Assert.assertThrows(
            "Placa: número inválido!",
            RuntimeException.class,
            () -> {
                new Placa("ABC123J").toString();
            }
        );
    }
}
