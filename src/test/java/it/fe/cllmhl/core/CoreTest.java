package it.fe.cllmhl.core;

import it.fe.cllmhl.Application;

import java.util.Locale;

import org.junit.Test;

public class CoreTest {
    
    ILogger mLog = ServiceLocator.getLogService().getLogger(CoreTest.class);

    @Test
    public void uncheckedExceptionTest() {
        UncheckedException llUncheckedException = new UncheckedException(CoreErrors.FATAL, "Testo a piacere di errore");
        System.out.println(llUncheckedException.getMessage());
        System.out.println(llUncheckedException.getMessage(Locale.ITALIAN));
        System.out.println(llUncheckedException.getMessage(Locale.ENGLISH));
    }
    
    @Test
    public void logTest() {
        mLog.debug("Debug message");
        mLog.info("Info message");
        mLog.error("Error message");
    }
    
    @Test
    public void configurationManagerTest() {
        Locale lLocale = Application.getLocale();
        mLog.info(lLocale);
    }
    
}
