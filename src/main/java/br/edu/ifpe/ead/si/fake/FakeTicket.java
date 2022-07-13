package br.edu.ifpe.ead.si.fake;

import br.edu.ifpe.ead.si.Placa;
import br.edu.ifpe.ead.si.Ticket;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.UUID;

public class FakeTicket implements Ticket {
    private final String titulo;
    private final String id;
    private final Placa placa;
    private final LocalDateTime dataHora;
    private final boolean validado;

    public FakeTicket(
        Placa placa,
        LocalDateTime dataHora,
        boolean validado
    ) {
        this(
            "MandacaruPark",
            UUID.randomUUID().toString().toUpperCase().substring(0, 6),
            placa,
            dataHora,
            validado
        );
    }

    public FakeTicket(
        String titulo,
        String id,
        Placa placa,
        LocalDateTime dataHora,
        boolean validado
    ) {
        this.titulo = titulo;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
        this.validado = validado;
    }

    @Override
    public void print(OutputStream papel) throws Exception {
        try (Writer lapis = new OutputStreamWriter(papel, "UTF-8")) {
            lapis.write(this.titulo);
            lapis.write(this.placa.toString());
        }
    }

    @Override
    public boolean validado() {
        return this.validado;
    }
}
