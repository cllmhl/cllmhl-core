package it.fe.cllmhl.core;


public interface IServiceFactory<T> {
    T getService();
    Class<T> getType();
}
