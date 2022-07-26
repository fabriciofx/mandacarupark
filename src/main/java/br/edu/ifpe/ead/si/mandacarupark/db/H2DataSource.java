package br.edu.ifpe.ead.si.mandacarupark.db;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class H2DataSource implements DataSource {
    private final String url;
    private final Driver driver;

    public H2DataSource(final String dbname) {
        this(new org.h2.Driver(), dbname);
    }

    public H2DataSource(final Driver drvr, final String dbname) {
        this.driver = drvr;
        this.url = String.format(
            "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS %s\\;SET SCHEMA %s",
            dbname,
            dbname,
            dbname
        );
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.driver.connect(this.url, new Properties());
    }

    @Override
    public Connection getConnection(String username, String password)
        throws SQLException {
        final Properties props = new Properties();
        props.put("user", username);
        props.put("password", password);
        return this.driver.connect(this.url, props);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("#getLogWriter()");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("#setLogWriter()");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("#setLoginTimeout()");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("#getLoginTimeout()");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("#getParentLogger()");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("#unwrap()");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("#isWrapperFor()");
    }
}
