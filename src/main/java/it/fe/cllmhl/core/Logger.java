package it.fe.cllmhl.core;


import org.apache.log4j.Level;


public final class Logger implements ILogger {
	
    private org.apache.log4j.Logger log4jLogger;

    protected Logger(org.apache.log4j.Logger log4jLogger) {
        this.log4jLogger = log4jLogger;
    }

    @Override
    public void error(Throwable throwable, Object... messageParts) {
        if (this.log4jLogger.isEnabledFor(Level.ERROR)) {
            this.log4jLogger.error(combineParts(messageParts), throwable);
        }
    }

    @Override
    public void error(Object... messageParts) {
        if (this.log4jLogger.isEnabledFor(Level.ERROR)) {
            this.log4jLogger.error(combineParts(messageParts));
        }
    }

    @Override
    public void info(Object... messageParts) {
        if (this.log4jLogger.isInfoEnabled()) {
            this.log4jLogger.info(combineParts(messageParts));
        }
    }

    @Override
    public void debug(Object... messageParts) {
        if (this.log4jLogger.isDebugEnabled()) {
            this.log4jLogger.debug(combineParts(messageParts));
        }
    }

    private String combineParts(Object[] messageParts) {
        StringBuilder builder = new StringBuilder();
        for (Object part : messageParts) {
            builder.append(part);
        }
        return builder.toString();
    }
}
