package it.fe.cllmhl.util;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class LocaleUtil {

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(LocaleUtil.class);

    public static I18nBean getI18nBean(Locale pLocale) {
        return new I18nBean(pLocale);
    }

    public static String getLocalizedDatePattern(String pStrPattern, Locale pLocale) {
        mLogger.debug("ENTER getLocalizedDatePattern ", pStrPattern, " in ", pLocale.toString());
        String lStrLocalizedPattern = new SimpleDateFormat(pStrPattern, pLocale).toLocalizedPattern();
        mLogger.debug("FINISH getLocalizedDatePattern returning ", lStrLocalizedPattern);
        return lStrLocalizedPattern;
    }

    public static String getLocalizedNumberPattern(String pStrPattern, Locale pLocale) {
        mLogger.debug("ENTER getLocalizedDatePattern ", pStrPattern, " in ", pLocale.toString());
        DecimalFormat lDecimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(pLocale);
        lDecimalFormat.applyPattern(pStrPattern);
        String lStrLocalizedPattern = lDecimalFormat.toLocalizedPattern();
        mLogger.debug("FINISH getLocalizedDatePattern returning ", lStrLocalizedPattern);
        return lStrLocalizedPattern;
    }
    
    private LocaleUtil(){}
}
