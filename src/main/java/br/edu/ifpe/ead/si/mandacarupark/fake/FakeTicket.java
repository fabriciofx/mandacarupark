package br.edu.ifpe.ead.si.mandacarupark.fake;

import br.edu.ifpe.ead.si.mandacarupark.Pagamento;
import br.edu.ifpe.ead.si.mandacarupark.Placa;
import br.edu.ifpe.ead.si.mandacarupark.Ticket;
import br.edu.ifpe.ead.si.mandacarupark.Uuid;
import java.time.LocalDateTime;
import java.util.Map;

public class FakeTicket implements Ticket {
    private final Map<Uuid, Pagamento> pagamentos;
    private final Uuid id;
    private final Placa placa;
    private final LocalDateTime dataHora;
    private final double valor;

    public FakeTicket(
        Map<Uuid, Pagamento> pagamentos,
        Placa placa,
        LocalDateTime dataHora
    ) {
        this(
            pagamentos,
            new Uuid(),
            placa,
            dataHora,
            0.0
        );
    }

    public FakeTicket(
        Map<Uuid, Pagamento> pagamentos,
        Uuid id,
        Placa placa,
        LocalDateTime dataHora,
        double valor
    ) {
        this.pagamentos = pagamentos;
        this.id = id;
        this.placa = placa;
        this.dataHora = dataHora;
        this.valor = valor;
    }

    @Override
    public Uuid id() {
        return this.id;
    }

    @Override
    public Placa placa() {
        return this.placa;
    }

    @Override
    public LocalDateTime dataHora() {
        return this.dataHora;
    }

    @Override
    public double valor() {
        Pagamento pagamento = this.pagamentos.get(this.id);
        return pagamento.valor();
    }

    @Override
    public boolean validado() {
        boolean result = false;
        Pagamento pagamento = this.pagamentos.get(this.id);
        if (pagamento != null) {
            result = true;
        }
        return result;
    }
}
