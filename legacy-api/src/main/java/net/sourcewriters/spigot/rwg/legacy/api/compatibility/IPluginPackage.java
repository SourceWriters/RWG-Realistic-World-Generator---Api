package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.version.Version;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IPluginPackage {

    @NonNull
    String getName();

    Plugin getPlugin();

    boolean isEnabled();

    boolean hasParsedVersion();

    Version getParsedVersion();

    String getVersion();

    default boolean isFromPlugin(final Class<?> clazz) {
        final Plugin plugin = getPlugin();
        if (!isEnabled()) {
            return false;
        }
        return clazz.getClassLoader().equals(plugin.getClass().getClassLoader());
    }

    default boolean isPlugin(final Plugin plugin) {
        return hasName(plugin.getName());
    }

    default boolean hasName(final String name) {
        return getName().equals(name);
    }

}
