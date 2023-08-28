package net.sourcewriters.spigot.rwg.legacy.api;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.data.argument.IArgumentMap;
import net.sourcewriters.spigot.rwg.legacy.api.data.argument.exception.ArgumentStack;

public abstract class VersionCompatibilityProvider {

    protected final <E> void set(final IArgumentMap map, final Class<E> clazz, final E value) {
        map.set(clazz.getSimpleName(), value);
    }
    
    public final <E> E getOrNull(final IArgumentMap map, final Class<E> clazz) {
        return map.get(clazz.getSimpleName(), clazz).orElse(null);
    }

    public final <E> E get(final IArgumentMap map, final ArgumentStack stack, final Class<E> clazz) {
        final String name = clazz.getSimpleName();
        return map.getOrStack(name, clazz, stack);
    }

    public abstract void provide(ILogger logger, IArgumentMap map);

    public abstract void setup(RealisticWorldGenerator api, Plugin plugin, ILogger logger);
    
    public void shutdown(RealisticWorldGenerator api, ILogger logger) {}

}
