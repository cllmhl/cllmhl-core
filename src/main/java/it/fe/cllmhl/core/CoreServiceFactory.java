package it.fe.cllmhl.core;


public class CoreServiceFactory implements ICoreServiceFactory {
	
	private static ILogService mLogService = new LogService();
	
	public ILogService getLogService(){
		return mLogService;
	}
}
