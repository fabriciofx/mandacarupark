package br.edu.ifpe.ead.si.mandacarupark.evento;

public interface Target<S> {
    void notifique(S source);
}
