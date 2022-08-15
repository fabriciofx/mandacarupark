package br.edu.ifpe.ead.si.mandacarupark;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

public final class TestDataHora {
    @Test
    public void asString() {
        final DataHora dataHora = new DataHora(
            LocalDateTime.of(2022, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("2022-08-14T07:49:20", dataHora.toString());
    }

    @Test
    public void data() {
        final DataHora dataHora = new DataHora(
            LocalDateTime.of(2022, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("14/08/2022", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void hora() {
        final DataHora dataHora = new DataHora(
            LocalDateTime.of(2022, 8, 14, 7, 49, 20)
        );
        Assert.assertEquals("14/08/2022", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void parseIso() {
        final DataHora dataHora = new DataHora(
            "2022-08-14 07:49:20.5"
        );
        Assert.assertEquals("14/08/2022", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }

    @Test
    public void parse() {
        final DataHora dataHora = new DataHora(
            "14/08/2022 07:49:20"
        );
        Assert.assertEquals("14/08/2022", dataHora.data());
        Assert.assertEquals("07:49:20", dataHora.hora());
    }
}
