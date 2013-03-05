/**
 * 
 */
package it.fe.cllmhl.util;

import it.fe.cllmhl.core.CoreErrors;
import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * @author mcallegari
 * 
 */
public final class ReflectionUtil {

    // logger
    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(ReflectionUtil.class);
    private static PropertyUtilsBean mPropertyUtilsBean = new PropertyUtilsBean();

    public static <T> T createInstance(Class<T> pClass) {
        mLogger.debug("Start createInstance ", pClass.getName());
        T lTInstance;
        try {
            lTInstance = pClass.newInstance();
        } catch (InstantiationException e) {
            mLogger.error(e, "getting error while instantiating ", pClass.getName());
            throw new UncheckedException(e, CoreErrors.REFLECTION, e.getMessage());
        } catch (IllegalAccessException e) {
            mLogger.error(e, "illegal access to ", pClass.getName(), " while instantiating");
            throw new UncheckedException(e, CoreErrors.REFLECTION, e.getMessage());
        }
        mLogger.debug("Finish createInstance");
        return lTInstance;
    }

    public static void setBeanProperty(Object pBean, String pStrProperty, Object pValue) {
        mLogger.debug("Start setBeanProperty bean:", pBean.getClass().getName(), " property:", pStrProperty, " value:", pValue + "");
        try {
            mPropertyUtilsBean.setSimpleProperty(pBean, pStrProperty, pValue);
        } catch (IllegalAccessException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.REFLECTION, e.getMessage());
        } catch (InvocationTargetException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.REFLECTION, e.getMessage());
        } catch (NoSuchMethodException e) {
            mLogger.error(e);
            throw new UncheckedException(e, CoreErrors.REFLECTION, e.getMessage());
        }
        mLogger.debug("Finish setBeanProperty");
    }
    
    private ReflectionUtil(){}
}
