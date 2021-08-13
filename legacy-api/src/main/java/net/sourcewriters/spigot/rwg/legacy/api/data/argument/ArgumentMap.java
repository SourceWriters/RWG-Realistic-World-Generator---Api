package net.sourcewriters.spigot.rwg.legacy.api.data.argument;

import java.util.HashMap;
import java.util.Objects;

public class ArgumentMap implements IArgumentMap {

    private final HashMap<String, Object> map = new HashMap<>();

    public boolean has(String key) {
        return map.containsKey(key);
    }

    public boolean has(String key, Class<?> type) {
        Object object = map.get(key);
        return object != null && type.isInstance(object);
    }

    public Option<Object> get(String key) {
        return Option.of(map.get(key));
    }

    public <E> Option<E> get(String key, Class<E> type) {
        return get(key).filter(type::isInstance).map(type::cast);
    }

    public ArgumentMap set(String key, Object value) {
        map.put(key, Objects.requireNonNull(value));
        return this;
    }

    public ArgumentMap remove(String key) {
        map.remove(key);
        return this;
    }

    public ArgumentMap clear() {
        map.clear();
        return this;
    }

    public ArgumentMap clone() {
        ArgumentMap clone = new ArgumentMap();
        map.putAll(map);
        return clone;
    }

}
