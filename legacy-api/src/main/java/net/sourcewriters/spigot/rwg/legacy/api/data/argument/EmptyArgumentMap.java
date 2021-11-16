package net.sourcewriters.spigot.rwg.legacy.api.data.argument;

public final class EmptyArgumentMap implements IArgumentMap {

    public static final EmptyArgumentMap INSTANCE = new EmptyArgumentMap();

    private EmptyArgumentMap() {}

    @Override
    public boolean has(final String key) {
        return false;
    }

    @Override
    public boolean has(final String key, final Class<?> type) {
        return false;
    }

    @Override
    public Option<Object> get(final String key) {
        return Option.empty();
    }

    @Override
    public <E> Option<E> get(final String key, final Class<E> type) {
        return Option.empty();
    }

    @Override
    public IArgumentMap set(final String key, final Object value) {
        return this;
    }

    @Override
    public IArgumentMap remove(final String key) {
        return this;
    }

    @Override
    public IArgumentMap clear() {
        return this;
    }

    @Override
    public IArgumentMap clone() {
        return this;
    }

}
