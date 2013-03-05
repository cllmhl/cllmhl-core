package it.fe.cllmhl.core;

public enum MessageLevel {
	INFO,
	WARNING,
	ERROR;
	
	public String getGenericKey(){
		return "jsp.message." + toString().toLowerCase();
	}
}
