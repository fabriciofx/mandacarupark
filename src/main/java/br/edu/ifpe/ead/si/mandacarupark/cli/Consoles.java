package br.edu.ifpe.ead.si.mandacarupark.cli;

public class Consoles {
    private final Console unix;
    private final Console windows;

    public Consoles() {
        this(new ConsoleUnix(), new ConsoleWindows());
    }

    public Consoles(final Console unix, final Console windows) {
        this.unix = unix;
        this.windows = windows;
    }

    public Console console() {
        final String os = System.getProperty("os.name");
        Console console = this.unix;
        if (os.contains("Windows")) {
            console = this.windows;
        }
        return console;
    }
}
