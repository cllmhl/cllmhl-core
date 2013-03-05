/**
 * 
 */
package it.fe.cllmhl.util;

import it.fe.cllmhl.core.CoreErrors;
import it.fe.cllmhl.core.ILogger;
import it.fe.cllmhl.core.ServiceLocator;
import it.fe.cllmhl.core.UncheckedException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.comparators.NullComparator;

/**
 * @author cllmhl
 * 
 */
public final class CollectionUtil {

    // logger
    private static ILogger mLogger = ServiceLocator.getLogService().getLogger(CollectionUtil.class);

    /**
     * CollectionUtils.filter wrapper. Warning: this method modifies the input
     * collection
     * 
     * @param pBeanCollection
     *            the collection of bean to filter
     * @param pProperty
     *            the bean property to check against the value
     * @param pValue
     *            the value required
     */
    public static void filter(Collection<?> pBeanCollection, String pProperty, Object pValue) {
        mLogger.debug("Filter start");
        Predicate lBeanPredicate = getPredicate(pProperty, pValue);
        CollectionUtils.filter(pBeanCollection, lBeanPredicate);
        mLogger.debug("Filter end");

    }

    /**
     * @param pBeanList
     *            the list of bean to sort
     * @param pProperty
     *            the property to build the map
     * @return the Map where in the key we have the property value and in the
     *         value we have the bean itself
     */
    public static Map<Object, Object> getAsHashMap(List<?> pBeanList, String pProperty) {
        mLogger.debug("getAsHashmap start");
        HashMap<Object, Object> lHashMap = new HashMap<Object, Object>();
        if ((pBeanList == null) || (pProperty == null)) {
            mLogger.error("List or property is null");
            throw new IllegalArgumentException("List or property is null");
        }

        for (Object lBean : pBeanList) {
            try {
                Object lPropertyValue = PropertyUtils.getProperty(lBean, pProperty);
                lHashMap.put(lPropertyValue, lBean);
            } catch (IllegalAccessException e) {
                mLogger.error(e, "An error occurred accessing the property  " + pProperty);
                throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
            } catch (InvocationTargetException e) {
                mLogger.error(e, "An error occurred accessing the property  " + pProperty);
                throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
            } catch (NoSuchMethodException e) {
                mLogger.error(e, "An error occurred accessing the property  " + pProperty);
                throw new UncheckedException(e, CoreErrors.FATAL, e.getMessage());
            }
        }

        mLogger.debug("getAsHashmap end");
        return lHashMap;
    }

    /**
     * @param pProperty
     * @param pValue
     * @return
     */
    private static Predicate getPredicate(String pProperty, Object pValue) {
        Predicate lPredicate;

        if (pValue == null) {
            lPredicate = PredicateUtils.nullPredicate();
        } else {
            lPredicate = PredicateUtils.equalPredicate(pValue);
        }

        return new BeanPredicate(pProperty, lPredicate);
    }

    /**
     * CollectionUtils.select wrapper.
     * 
     * @param pBeanCollection
     *            the collection of bean we need to filter
     * @param pProperty
     *            the bean property to check against the value
     * @param pValue
     *            the required value
     * @return the collection of beans with the matching elements
     */
    public static Collection<?> select(Collection<?> pBeanCollection, String pProperty, Object pValue) {
        mLogger.debug("Select start");
        Predicate lBeanPredicate = getPredicate(pProperty, pValue);
        Collection<?> lReturnCollection = CollectionUtils.select(pBeanCollection, lBeanPredicate);
        mLogger.debug("Collection Selected.");
        return lReturnCollection;
    }

    /**
     * CollectionUtils.selectRejected wrapper.
     * 
     * @param pBeanCollection
     *            the collection of bean we need to filter
     * @param pProperty
     *            the bean property to check against the value
     * @param pValue
     *            the rejected value
     * @return the collection of beans with the elements whose pProperty doesn't
     *         match the value
     */
    public static Collection<?> selectRejected(Collection<?> pBeanCollection, String pProperty, Object pValue) {
        mLogger.debug("SelectRejected Start");
        if ((pBeanCollection == null) || (pProperty == null)) {
            mLogger.error("Collection is null");
            throw new IllegalArgumentException("Collection is null");
        }
        Predicate lBeanPredicate = getPredicate(pProperty, pValue);
        Collection<?> lReturnCollection = CollectionUtils.selectRejected(pBeanCollection, lBeanPredicate);
        mLogger.debug("SelectRejected End");
        return lReturnCollection;
    }

    /**
     * Collections.sort wrapper.
     * 
     * @param pBeanList
     *            the list of bean to sort
     * @param pProperty
     *            the property to use for sorting (getter method!)
     */
    @SuppressWarnings("unchecked")
    public static void sort(List<?> pBeanList, String pProperty) {
        mLogger.debug("Sort start");
        BeanComparator lBeanComparator = new BeanComparator(pProperty, new NullComparator());
        Collections.sort(pBeanList, lBeanComparator);
        mLogger.debug("Sort end");
    }
    
    private CollectionUtil(){}
}
