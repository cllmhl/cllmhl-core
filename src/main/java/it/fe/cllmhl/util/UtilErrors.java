package it.fe.cllmhl.util;

import it.fe.cllmhl.core.IError;


public enum UtilErrors implements IError{
		REFLECTION(200),
		IO(300),
		FILE_CREATION(301);
		
		private final int code;
		
		UtilErrors(int code) {
			this.code = code;
		}

		public int getCode() { return code; }

		public String getResourceBundle() {return getBundle();}
		
		//mi serve per i18n
		public static String getBundle(){return "cllmhl-utils";};
}
