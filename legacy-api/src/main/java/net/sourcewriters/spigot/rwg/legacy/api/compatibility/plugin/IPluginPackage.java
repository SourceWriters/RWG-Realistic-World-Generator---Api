package net.sourcewriters.spigot.rwg.legacy.api.compatibility.plugin;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.version.Version;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IPluginPackage {

    @NonNull
    public String getName();

    public Plugin getPlugin();

    public Version getVersion();

    @NonNull
    public String getVersionRaw();

    public default boolean isFromPlugin(Class<?> clazz) {
        Plugin plugin = getPlugin();
        if (!plugin.isEnabled()) {
            return false;
        }
        return clazz.getClassLoader().equals(plugin.getClass().getClassLoader());
    }

    public default boolean isPlugin(Plugin plugin) {
        return hasName(plugin.getName());
    }

    public default boolean hasName(String name) {
        return getName().equals(name);
    }

}
