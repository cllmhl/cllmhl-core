package it.fe.cllmhl;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class Installation {
    
    private static java.util.logging.Logger mLogger = java.util.logging.Logger.getLogger(Installation.class.getName());
    
    private static String home = null;
    static {
        //Standard environment first guess
        File lFileHomeDirectory = new File("home");
        if (lFileHomeDirectory.exists()) {
            home = lFileHomeDirectory.getAbsolutePath().concat(File.separator);
            mLogger.info("Application home: "+home);
        }
        //Standard environment second guess
        if (home == null) {
            lFileHomeDirectory = new File(System.getProperty("user.dir")+File.separator+"home");
            if (lFileHomeDirectory.exists()) {
                home = lFileHomeDirectory.getAbsolutePath().concat(File.separator);
                mLogger.info("Application home: "+home);
            }
        }
        //Container
        if (home == null) {
            try {
                Context lContext = (Context) new InitialContext().lookup("java:comp/env");
                home = (String) lContext.lookup("cllmhl-home");
            } catch (NamingException e) {
            }
        }
        if (home == null) {
            mLogger.info("FATAL ERROR!!! Unable to establish an Application home directory");
        }
    }
    public static String getConfigurationDirectory() {
        return(home.concat(File.separator).concat("conf").concat(File.separator));
    }

    public static String getTempDirectory() {
        return(home.concat(File.separator).concat("temp").concat(File.separator));
    }
    
    //Utility class
    private Installation(){}
}
