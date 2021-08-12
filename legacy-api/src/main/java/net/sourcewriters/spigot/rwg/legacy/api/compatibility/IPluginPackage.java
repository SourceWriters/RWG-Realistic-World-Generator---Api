package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.version.Version;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IPluginPackage {

    @NonNull
    public String getName();

    public Plugin getPlugin();
    
    public boolean isEnabled();
    
    public boolean hasParsedVersion();

    public Version getParsedVersion();
    
    public String getVersion();

    public default boolean isFromPlugin(Class<?> clazz) {
        Plugin plugin = getPlugin();
        if (!isEnabled()) {
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
