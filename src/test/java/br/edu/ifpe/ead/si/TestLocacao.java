package br.edu.ifpe.ead.si;

import br.edu.ifpe.ead.si.fake.FakeEntradas;
import br.edu.ifpe.ead.si.fake.FakeSaidas;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLocacao {
    @Test
    void realizaLocacao() {
        Entradas entradas = new FakeEntradas();
        Saidas saidas = new FakeSaidas();
        LocalDateTime dataHora = LocalDateTime.now();
        Entrada entrada = entradas.entrada(new Placa("ABC1234"), dataHora);
        Saida saida = saidas.saida(entrada, dataHora.plusMinutes(60));
        assertEquals(saida.placa(), new Placa("ABC1234"));
    }
}
