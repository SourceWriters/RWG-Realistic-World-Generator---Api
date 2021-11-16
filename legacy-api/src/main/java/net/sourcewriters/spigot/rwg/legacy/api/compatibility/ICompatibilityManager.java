package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface ICompatibilityManager {

    boolean isExternal(Plugin plugin);

    @NonNull
    IPluginPackage getPackage(@NonNull String name);

    @NonNull
    IPluginPackage getPackage(@NonNull Plugin plugin);

    boolean register(@NonNull Plugin owner, @NonNull Class<? extends CompatibilityAddon> addon, String targetPlugin)
        throws AddonInitializationException;

    boolean register(@NonNull CompatibilityBlockPlacer placer);

    boolean register(@NonNull CompatibilityBlockParser parser);

    boolean register(@NonNull CompatibilityBlockLoader loader);

    boolean register(@NonNull CompatibilitySchematicUpdate<?> update);

    boolean register(@NonNull CompatibilitySchematicConverter converter);

}
