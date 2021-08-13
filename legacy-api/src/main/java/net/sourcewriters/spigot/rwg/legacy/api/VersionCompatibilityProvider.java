package net.sourcewriters.spigot.rwg.legacy.api;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.data.argument.IArgumentMap;

public abstract class VersionCompatibilityProvider {

    public abstract void provide(ILogger logger, IArgumentMap map);

    public abstract void setup(Plugin plugin, ILogger logger);

}
