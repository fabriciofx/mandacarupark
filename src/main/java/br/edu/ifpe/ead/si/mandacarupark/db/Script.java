package br.edu.ifpe.ead.si.mandacarupark.db;

public interface Script {
    void run(Session session) throws Exception;
}
