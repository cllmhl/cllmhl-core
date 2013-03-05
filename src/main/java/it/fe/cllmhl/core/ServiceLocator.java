package it.fe.cllmhl.core;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@SuppressWarnings("rawtypes")
public final class ServiceLocator {

    private static ICoreServiceFactory mCoreServiceFatory = new CoreServiceFactory();
    private static Map<Class<?>, IServiceFactory<?>> mServicesFactory = new HashMap<Class<?>, IServiceFactory<?>>();
    private static Map<Class<?>, INamedServiceFactory<?>> mNamedServicesFactory = new HashMap<Class<?>, INamedServiceFactory<?>>();

    static {
        // Se si vogliono cambiare le implementazioni dei servizi di base come ILogService
        // Bisogna andare in override con un jar che ha una implementazione configurata in META-INF/services
        ServiceLoader<ICoreServiceFactory> lCoreServiceFactoryServiceLoader = ServiceLoader.load(ICoreServiceFactory.class);
        for (ICoreServiceFactory lServiceProviderFactory : lCoreServiceFactoryServiceLoader) {
            mCoreServiceFatory = lServiceProviderFactory;
        }

        // Se si vogliono registrare degli altri servizi "anonimi" e/o "qualificati" possiamo usare la stessa
        // logica andando ad implementare dei generici IServiceFacory configurati sempre in META-INF/services
        // che pero' non possono andare in override di servizi gia' registrati
        ServiceLoader<IServiceFactory> lServiceFactoryLoader = ServiceLoader.load(IServiceFactory.class);
        for (IServiceFactory lServiceFactory : lServiceFactoryLoader) {
            mServicesFactory.put(lServiceFactory.getType(), lServiceFactory);
        }

        ServiceLoader<INamedServiceFactory> lNamedServiceFactoryLoader = ServiceLoader.load(INamedServiceFactory.class);
        for (INamedServiceFactory lNamedServiceFactory : lNamedServiceFactoryLoader) {
            mNamedServicesFactory.put(lNamedServiceFactory.getType(), lNamedServiceFactory);
        }
    }

    public static ILogService getLogService() {
        return mCoreServiceFatory.getLogService();
    }

    public static <T> T getService(Class<T> pClass) {
        IServiceFactory factory = mServicesFactory.get(pClass);
        if (factory != null) {
            return pClass.cast(factory.getService());
        } else {
            return null;
        }
    }

    public static <T> T getServiceByName(Class<T> pClass, String pStrName) {
        INamedServiceFactory factory = mNamedServicesFactory.get(pClass);
        if (factory != null) {
            return pClass.cast(factory.getService(pStrName));
        } else {
            return null;
        }
    }

    private ServiceLocator() {
    }
}
