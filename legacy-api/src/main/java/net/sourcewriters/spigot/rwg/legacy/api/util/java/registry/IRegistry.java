package net.sourcewriters.spigot.rwg.legacy.api.util.java.registry;

import java.util.List;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.IKeyed;
import com.syntaxphoenix.syntaxapi.utils.key.INamespace;

public interface IRegistry<E extends IKeyed> {

    List<E> getByNamespace(INamespace<?> namespace);

    List<IKey> getKeys();

    boolean unregister(IKey key);

    boolean register(E object);

    boolean has(IKey key);

    E get(IKey key);

}
