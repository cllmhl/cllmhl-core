package it.fe.cllmhl.core;


public enum CoreErrors implements IError {
	FATAL(1),
    REFLECTION(200),
    IO(300),
    FILE_CREATION(301);

    public static final String RESOURCE_BUNDLE = "cllmhl-core";

    private final int code;

	CoreErrors(int code) {
		this.code = code;
	}

	public int getCode() { return code; };
	public String getResourceBundle(){return RESOURCE_BUNDLE;}
}
