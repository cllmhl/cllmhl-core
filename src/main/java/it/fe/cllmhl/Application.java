package it.fe.cllmhl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

/**
 * @author cllmhl
 * 
 *         This class reads the application.xml file
 */
public final class Application {
    private static java.util.logging.Logger mLogger = java.util.logging.Logger.getLogger(Application.class.getName());

    private static ApplicationBean mApplicationBean = buildApplicationBean();

    /**
     * @return the application mode production/development/test
     */
    public static String getMode() {
        return mApplicationBean.getMode();
    }

    /**
     * @return the application version. In development mode it returns a timestamp instead of the configured string
     */
    public static String getVersion() {
        String lStrAppVersion = mApplicationBean.getVersion();
        // Se non siamo in produzione la versione diventa un timestamp che mi
        // risparmia problemi di cache su certe risorse
        if (!getMode().equalsIgnoreCase("production")) {
            lStrAppVersion = "ts" + Calendar.getInstance().getTimeInMillis();
        }
        return lStrAppVersion;
    }

    /**
     * @return the application locale. In applicatio.xml it must be configured as en_EN, it_UT, ...
     */
    public static Locale getLocale() {
        String[] lStrSplittedLoacle = mApplicationBean.getLocale().split("_");
        return new Locale(lStrSplittedLoacle[0], lStrSplittedLoacle[1]);
    }

    /**
     * @return the theme configured in application.xml. For web applications, most of the times, it is a jqueryui theme.
     */
    public static String getTheme() {
        return mApplicationBean.getTheme();
    }

    private static ApplicationBean buildApplicationBean() {
        mLogger.fine("Start buildApplicationBean");
        // Standard properties file reading
        Properties lProperties = new Properties();
        File lFile = new File(Installation.getConfigurationDirectory().concat("application.properties"));
        FileInputStream lFileInputStream = null;
        try {
            lFileInputStream = new FileInputStream(lFile);
            // loading properties from properties file
            lProperties.load(lFileInputStream);
        } catch (IOException e) {
            mLogger.severe(e.toString());
        } finally {
            if (lFileInputStream != null) {
                try {
                    lFileInputStream.close();
                } catch (IOException e) {
                    mLogger.severe(e.toString());
                }
            }
        }

        // reading property
        ApplicationBean lApplicationBean = new ApplicationBean();
        lApplicationBean.setMode(lProperties.getProperty("mode"));
        lApplicationBean.setVersion(lProperties.getProperty("version"));
        lApplicationBean.setTheme(lProperties.getProperty("theme"));
        lApplicationBean.setLocale(lProperties.getProperty("locale"));
        mLogger.fine("Finish buildApplicationBean " + lApplicationBean.toString());
        return lApplicationBean;
    }

    private Application() {
    }
}
