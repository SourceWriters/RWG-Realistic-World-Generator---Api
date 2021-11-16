package net.sourcewriters.spigot.rwg.legacy.api.version.handle;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class AbstractClassLookupCache<R extends ClassLookup> {

    protected final HashMap<String, R> cache = new HashMap<>();

    public void clear() {
        cache.values().forEach(ClassLookup::uncache);
    }

    @NonNull
    public Optional<R> get(final String name) {
        return Optional.ofNullable(cache.get(name));
    }

    public boolean has(final String name) {
        return cache.containsKey(name);
    }

    @NonNull
    public R create(@NonNull final String name, @NonNull final String path) {
        Objects.requireNonNull(name, "String name can't be null!");
        Objects.requireNonNull(name, "String path can't be null!");
        if (has(name)) {
            return cache.get(name);
        }
        final R reflect = create(path);
        cache.put(name, reflect);
        return reflect;
    }

    @NonNull
    public R create(@NonNull final String name, @NonNull final Class<?> clazz) {
        Objects.requireNonNull(name, "String name can't be null!");
        Objects.requireNonNull(clazz, "Class can't be null!");
        if (has(name)) {
            return cache.get(name);
        }
        final R reflect = create(clazz);
        cache.put(name, reflect);
        return reflect;
    }

    public void delete(final String name) {
        final ClassLookup lookup = cache.remove(name);
        if (lookup != null) {
            lookup.delete();
        }
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Entry<String, R>[] entries() {
        return cache.entrySet().toArray(Entry[]::new);
    }

    protected abstract R create(Class<?> clazz);

    protected abstract R create(String path);

}
