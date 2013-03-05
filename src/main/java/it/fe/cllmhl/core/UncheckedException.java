package it.fe.cllmhl.core;

import java.util.Locale;

public class UncheckedException extends RuntimeException implements IMessage {
	
	private static final String ERROR_MESSAGES_PREFIX = "error.message.";
	private static final long serialVersionUID = 1L;
    //Errore
    private IError error;
    //Messaggio
    private IMessage message;
    
    public UncheckedException(String args) {
        this.error = CoreErrors.FATAL;
        this.message = new Message(MessageLevel.ERROR, CoreErrors.FATAL.getResourceBundle(), ERROR_MESSAGES_PREFIX + error.getCode(), args);
    }

    public UncheckedException(Throwable pThrowable) {
        this.error = CoreErrors.FATAL;
        this.message = new Message(MessageLevel.ERROR, CoreErrors.FATAL.getResourceBundle(), ERROR_MESSAGES_PREFIX + error.getCode(), pThrowable.getMessage());
    }

	public UncheckedException(IError error, String... args) {
		this.error = error;
		this.message = new Message(MessageLevel.ERROR, error.getResourceBundle(), ERROR_MESSAGES_PREFIX + error.getCode(), args);
	}

	public UncheckedException(Throwable pThrowable, IError error, String... args) {
		super(pThrowable.getMessage(),pThrowable);
		this.error = error;
		this.message = new Message(MessageLevel.ERROR, error.getResourceBundle(), ERROR_MESSAGES_PREFIX + error.getCode(), args);
	}

	public MessageLevel getMessageLevel() {
		return MessageLevel.ERROR;
	}

	public String getMessage(Locale pLocale) {
		return message.getMessage(pLocale);
	}

	public IError getError() {
		return error;
	}
}
