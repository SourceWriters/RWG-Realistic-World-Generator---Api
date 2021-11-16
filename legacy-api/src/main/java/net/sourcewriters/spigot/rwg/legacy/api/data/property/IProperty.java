package net.sourcewriters.spigot.rwg.legacy.api.data.property;

import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public interface IProperty<E> {

    E getValue();

    E getValueOr(E fallback);

    String getKey();

    Class<?> getOwner();

    boolean isInstance(Class<?> sample);

    <V> IProperty<V> cast(Class<V> sample);

    <V> IProperty<V> map(Function<E, V> mapper);

    boolean isEmpty();

    default boolean isPresent() {
        return !isEmpty();
    }

    @SuppressWarnings("unchecked")
    static <V> IProperty<V> of(final String key, final V value) {
        return value == null ? new VoidProperty(key)
            : Primitives.isInstance(value) ? new PrimitiveProperty<>(key, value) : new Property<>(key, value);
    }

}
