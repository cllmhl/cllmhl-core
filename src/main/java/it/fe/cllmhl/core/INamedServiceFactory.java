package it.fe.cllmhl.core;

public interface INamedServiceFactory<T> {
    T getService(String name);

    Class<T> getType();
}
