package net.sourcewriters.spigot.rwg.legacy.api.data.argument;

import java.util.HashMap;
import java.util.Objects;

public class ArgumentMap implements IArgumentMap {

    private final HashMap<String, Object> map = new HashMap<>();

    @Override
    public boolean has(final String key) {
        return map.containsKey(key);
    }

    @Override
    public boolean has(final String key, final Class<?> type) {
        final Object object = map.get(key);
        return object != null && type.isInstance(object);
    }

    @Override
    public Option<Object> get(final String key) {
        return Option.of(map.get(key));
    }

    @Override
    public <E> Option<E> get(final String key, final Class<E> type) {
        return get(key).filter(type::isInstance).map(type::cast);
    }

    @Override
    public ArgumentMap set(final String key, final Object value) {
        map.put(key, Objects.requireNonNull(value));
        return this;
    }

    @Override
    public ArgumentMap remove(final String key) {
        map.remove(key);
        return this;
    }

    @Override
    public ArgumentMap clear() {
        map.clear();
        return this;
    }

    @Override
    public ArgumentMap clone() {
        final ArgumentMap clone = new ArgumentMap();
        map.putAll(map);
        return clone;
    }

}
