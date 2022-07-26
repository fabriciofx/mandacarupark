package br.edu.ifpe.ead.si.mandacarupark.db;

import java.sql.Connection;

public interface Session {
    Connection connection() throws Exception;
}
