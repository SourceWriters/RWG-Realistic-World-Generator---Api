package net.sourcewriters.spigot.rwg.legacy.api.util.java.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.IKeyed;
import com.syntaxphoenix.syntaxapi.utils.key.INamespace;

public class SimpleRegistry<E extends IKeyed> implements IRegistry<E> {

    protected final HashMap<MapKey, E> map = new HashMap<>();

    @Override
    public List<E> getByNamespace(final INamespace<?> namespace) {
        final Set<MapKey> keys = map.keySet();
        final ArrayList<E> output = new ArrayList<>();
        for (final MapKey key : keys) {
            if (key.getKey().getNamespace().isSimilar(namespace)) {
                output.add(map.get(key));
            }
        }
        return output;
    }

    @Override
    public List<IKey> getKeys() {
        return map.keySet().stream().map(MapKey::getKey).toList();
    }

    @Override
    public boolean unregister(final IKey key) {
        return false;
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public boolean register(final E object) {
        final IKey key = object.getKey();
        if (key == null || map.containsKey(key)) {
            return false;
        }
        map.put(new MapKey(key), object);
        return true;
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public boolean has(final IKey key) {
        return map.containsKey(key);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public E get(final IKey key) {
        return map.get(key);
    }

}
