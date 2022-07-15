package br.edu.ifpe.ead.si.mandacarupark;

import java.util.Objects;
import java.util.UUID;

public final class Uuid {
    private final String numero;

    public Uuid() {
        this(UUID.randomUUID().toString().toUpperCase().replaceAll("-",""));
    }

    public Uuid(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Uuid &&
            Uuid.class.cast(obj).numero.equals(this.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.numero);
    }

    @Override
    public String toString() {
        return this.numero;
    }
}
