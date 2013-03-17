package it.fe.cllmhl.core;


import it.fe.cllmhl.Installation;

import org.apache.log4j.LogManager;
import org.apache.log4j.MDC;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.bridge.SLF4JBridgeHandler;


final class LogService implements ILogService {
	
    private static String mStrConfigurationFile;
    
    //initializzation of log4j and SLF4JBridging
    static {
        SLF4JBridgeHandler.install();
        mStrConfigurationFile = Installation.getConfigurationDirectory().concat("log4j.xml");
        DOMConfigurator.configure(mStrConfigurationFile);
    }

    protected LogService(){}
    
    @Override
    public ILogger getLogger(Class<?> clazz) {
        return new Logger(org.apache.log4j.Logger.getLogger(clazz));
    }

    @Override
    public ILogger getLogger(String name) {
        return new Logger(org.apache.log4j.Logger.getLogger(name));
    }

    @Override
    public void reloadConfiguration() {
    	LogManager.resetConfiguration();
    	DOMConfigurator.configure(mStrConfigurationFile);
    }
    
    @Override
    public void mdcPut(String pStrKey, String pStrValue) {
    	MDC.put(pStrKey,pStrValue);
    }
    
    @Override
    public void mdcRemove(String pStrKey) {
    	MDC.remove(pStrKey);
    }
}
