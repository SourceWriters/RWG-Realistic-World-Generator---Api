package net.sourcewriters.spigot.rwg.legacy.api;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.data.argument.IArgumentMap;
import net.sourcewriters.spigot.rwg.legacy.api.data.argument.exception.ArgumentStack;

public abstract class VersionCompatibilityProvider {

    protected final <E> void set(IArgumentMap map, Class<E> clazz, E value) {
        map.set(clazz.getSimpleName(), value);
    }

    public final <E> E get(IArgumentMap map, ArgumentStack stack, Class<E> clazz) {
        String name = clazz.getSimpleName();
        return map.getOrStack(name, clazz, stack);
    }

    public abstract void provide(ILogger logger, IArgumentMap map);

    public abstract void setup(RealisticWorldGenerator api, Plugin plugin, ILogger logger);

}
