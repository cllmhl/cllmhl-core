package it.fe.cllmhl.core;

public interface ILogService {

    ILogger getLogger(Class<?> clazz);

    ILogger getLogger(String name);

    void reloadConfiguration();

    void mdcPut(String pStrKey, String pStrValue);

    void mdcRemove(String pStrKey);

}
