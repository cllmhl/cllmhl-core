package it.fe.cllmhl.util;

import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.IMessage;
import it.fe.cllmhl.core.Message;
import it.fe.cllmhl.core.MessageLevel;
import it.fe.cllmhl.core.ServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.beanutils.locale.converters.DoubleLocaleConverter;
import org.apache.commons.beanutils.locale.converters.FloatLocaleConverter;
import org.apache.commons.beanutils.locale.converters.IntegerLocaleConverter;
import org.apache.commons.beanutils.locale.converters.LongLocaleConverter;
import org.apache.commons.beanutils.locale.converters.SqlDateLocaleConverter;
import org.apache.commons.beanutils.locale.converters.SqlTimeLocaleConverter;
import org.apache.commons.beanutils.locale.converters.SqlTimestampLocaleConverter;

public final class BeanUtil {

    /** Enumeration dei pattern di conversione. */
    public enum Pattern {
        integer, decimal, date, time, timestamp;

        public String getPatternForLocale(Locale pLocale) {
            // carico il resource bundle corrispondente alla Locale passata
            ResourceBundle resourceBundle = ResourceBundle.getBundle("cllmhl-utils", pLocale);
            // Leggo la stringa
            return resourceBundle.getString("pattern." + this.toString());
        }
    }

    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(BeanUtil.class);

    private static Map<Locale, BeanUtilsBean> mBeanMap = new HashMap<Locale, BeanUtilsBean>();

    private static String[] emptyToNull(String[] value) {
        String[] ret = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            ret[i] = ("".equals(value[i]) ? null : value[i]);
        }
        return ret;
    }

    public static BeanUtilsBean getBeanUtilsBean(Locale pLocale) {
        mLogger.debug("Start getBeanUtilsBean()");

        BeanUtilsBean lBeanUtilsBean = mBeanMap.get(pLocale);
        // Se � in mappa lo ritorno!
        if (lBeanUtilsBean != null) {
            mLogger.debug("Using bean in the map");
        }
        // Altrimenti lo creo e lo metto in mappa
        else {
            mLogger.debug("Creating a new BeanUtilsBean for locale ", pLocale.toString());
            BeanUtilsBean.setInstance(new BeanUtilsBean2());
            ConvertUtilsBean2 lConvertUtilsBean2 = new ConvertUtilsBean2();
            registerLocaleConverters(pLocale, lConvertUtilsBean2);
            lBeanUtilsBean = new BeanUtilsBean(lConvertUtilsBean2, new PropertyUtilsBean());
            mBeanMap.put(pLocale, lBeanUtilsBean);
        }
        mLogger.debug("Finish getBeanUtilsBean()");
        return lBeanUtilsBean;
    }

    public static List<IMessage> populate(Object pObject, Map<String, String[]> pParameterMap, Locale pLocale) {

        mLogger.debug("ENTER populate()");
        List<IMessage> lListReturn = new ArrayList<IMessage>();
        BeanUtilsBean lBeanUtilsBean = getBeanUtilsBean(pLocale);

        for (Object key : pParameterMap.keySet()) {
            if (key == null) {
                continue;
            }
            // do not handle file objects (see FileFilter)
            if (key instanceof String) {
                String fieldName = (String) key;
                String[] value = pParameterMap.get(fieldName);
                value = emptyToNull(value);
                try {
                    lBeanUtilsBean.copyProperty(pObject, fieldName, value[0]);
                } catch (Exception e) {
                    lListReturn.add(new Message(MessageLevel.ERROR, "cllmhl-utils", "beanUtil.populateError", fieldName + ": " + e.getMessage()));
                    mLogger.error(e, "Populate error on field ", fieldName);
                }

            }
        }
        mLogger.debug("EXIT populate()");
        return lListReturn;
    }

    private static void registerLocaleConverters(Locale pLocale, ConvertUtilsBean2 pConvertUtilsBean) {
        mLogger.debug("Start registerLocaleConverters");
        // Per costruire i converter non usiamo un valore default altrimenti
        // sopprimono un mucchio di errori
        // Inoltre usiamo dei pattern non localizzati perch� � molto pi�
        // semplice ed intuitivo
        // java.lang.Integer
        IntegerLocaleConverter lIntegerLocaleConverter = new IntegerLocaleConverter(pLocale, Pattern.integer.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lIntegerLocaleConverter, java.lang.Integer.class);
        // java.lang.Long
        LongLocaleConverter lLongLocaleConverter = new LongLocaleConverter(pLocale, Pattern.integer.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lLongLocaleConverter, java.lang.Long.class);
        // java.lang.Float
        FloatLocaleConverter lFloatLocaleConverter = new FloatLocaleConverter(pLocale, Pattern.decimal.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lFloatLocaleConverter, java.lang.Float.class);
        // java.lang.Double
        DoubleLocaleConverter lDoubleLocaleConverter = new DoubleLocaleConverter(pLocale, Pattern.decimal.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lDoubleLocaleConverter, java.lang.Double.class);
        // java.math.BigDecimal
        BigDecimalLocaleConverter lBigDecimalLocaleConverter = new BigDecimalLocaleConverter(pLocale, Pattern.decimal.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lBigDecimalLocaleConverter, java.math.BigDecimal.class);
        // java.util.Date
        DateLocaleConverter lDateLocaleConverter = new DateLocaleConverter(pLocale, Pattern.timestamp.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lDateLocaleConverter, java.util.Date.class);
        // java.sql.Date
        SqlDateLocaleConverter lSqlDateLocaleConverter = new SqlDateLocaleConverter(pLocale, Pattern.date.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lSqlDateLocaleConverter, java.sql.Date.class);
        // java.sql.Time
        SqlTimeLocaleConverter lSqlTimeLocaleConverter = new SqlTimeLocaleConverter(pLocale, Pattern.time.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lSqlTimeLocaleConverter, java.sql.Time.class);
        // java.sql.Timestamp
        SqlTimestampLocaleConverter lSqlTimestampLocaleConverter = new SqlTimestampLocaleConverter(pLocale, Pattern.timestamp.getPatternForLocale(pLocale), false);
        pConvertUtilsBean.register(lSqlTimestampLocaleConverter, java.sql.Timestamp.class);
        mLogger.debug("Finish registerLocaleConverters");
    }

    private BeanUtil() {
    }
}
