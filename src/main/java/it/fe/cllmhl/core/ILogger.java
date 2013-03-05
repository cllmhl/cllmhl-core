package it.fe.cllmhl.core;

public interface ILogger {

    void error(Throwable throwable, Object... messageParts);

    void error(Object... messageParts);

    void info(Object... messageParts);

    void debug(Object... messageParts);

}
