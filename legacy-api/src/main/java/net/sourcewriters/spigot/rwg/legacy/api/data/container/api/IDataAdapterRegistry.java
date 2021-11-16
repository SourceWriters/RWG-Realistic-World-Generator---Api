package net.sourcewriters.spigot.rwg.legacy.api.data.container.api;

public interface IDataAdapterRegistry<B> {

    Class<B> getBase();

    boolean has(Class<?> clazz); // == isRegistered

    Object extract(B base);

    B wrap(Object value);

}
