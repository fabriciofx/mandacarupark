package br.edu.ifpe.ead.si.mandacarupark.db;

import java.io.IOException;

public interface Server extends AutoCloseable {
    void start() throws Exception;
    void stop() throws Exception;
    Session session();
    @Override
    void close() throws IOException;
}
