package br.edu.ifpe.ead.si;

import java.io.OutputStream;

public interface Ticket {
    void print(OutputStream papel) throws Exception;
    boolean validado();
}
