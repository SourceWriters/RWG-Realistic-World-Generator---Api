package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface ICompatibilityManager {

    abstract boolean isExternal(Plugin plugin);

    @NonNull
    abstract IPluginPackage getPackage(@NonNull String name);

    @NonNull
    abstract IPluginPackage getPackage(@NonNull Plugin plugin);
    
    abstract boolean register(@NonNull Plugin owner, @NonNull Class<? extends CompatibilityAddon> addon, String targetPlugin) throws AddonInitializationException;
    
    abstract boolean register(@NonNull CompatibilityBlockPlacer placer);
    
    abstract boolean register(@NonNull CompatibilityBlockParser parser);
    
    abstract boolean register(@NonNull CompatibilityBlockLoader loader);

}
