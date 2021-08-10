package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.compatibility.plugin.IPluginPackage;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface ICompatibilityManager {

    abstract boolean isExternal(Plugin plugin);

    @NonNull
    abstract IPluginPackage getPackage(@NonNull String name);
    
    abstract boolean register(@NonNull CompatibilityBlockPlacer placer);
    
    abstract boolean register(@NonNull CompatibilityAddon addon);

}
