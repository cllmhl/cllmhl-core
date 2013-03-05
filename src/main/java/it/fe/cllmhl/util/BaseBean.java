package it.fe.cllmhl.util;


/**
 * @author cllmhl
 * 
 * Used for override the toString() method for every bean in the application
 * 
 */
public abstract class BaseBean {

    @Override
    public String toString() {
        return StringUtil.reflectionToString(this);
    }
}
