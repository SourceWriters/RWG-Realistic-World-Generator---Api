package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.compatibility.plugin.IPluginPackage;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilityAddon {

    private final boolean external;
    private final Plugin owner;
    private final IPluginPackage plugin;

    private final String id;

    public CompatibilityAddon(ICompatibilityManager manager, Plugin owner, String name) {
        Objects.requireNonNull(manager, "ICompatibilityManager can't be null!");
        Objects.requireNonNull(owner, "Plugin can't be null!");
        Objects.requireNonNull(name, "Target plugin name can't be null!");
        Preconditions.checkArgument(name.isBlank(), "Target plugin name can't be empty!");
        this.external = manager.isExternal(owner);
        this.owner = owner;
        this.id = owner.getName() + "-" + name;
        Preconditions.checkArgument(manager.register(this), "Unable to register CompatibilityAddon '" + id + "'!");
        this.plugin = manager.getPackage(name);
    }

    public final boolean isExternal() {
        return external;
    }

    @NonNull
    public final Plugin getOwner() {
        return owner;
    }

    @NonNull
    public final IPluginPackage getTarget() {
        return plugin;
    }

    @NonNull
    public final String getId() {
        return id;
    }

    public final void enable(RealisticWorldGenerator realisticApi) {
        try {
            onEnable(realisticApi, plugin);
        } catch (Exception exception) {
            realisticApi.getLogger().log(LogTypeId.ERROR, "Failed to enable CompatibilityAddon '" + id + "'!");
            realisticApi.getLogger().log(LogTypeId.ERROR, exception);
        }
    }

    public final void disable(RealisticWorldGenerator realisticApi) {
        try {
            onDisable(realisticApi, plugin);
        } catch (Exception exception) {
            realisticApi.getLogger().log(LogTypeId.ERROR, "Failed to disable CompatibilityAddon '" + id + "'!");
            realisticApi.getLogger().log(LogTypeId.ERROR, exception);
        }
    }

    protected abstract void onEnable(RealisticWorldGenerator realisticApi, IPluginPackage plugin) throws Exception;

    protected abstract void onDisable(RealisticWorldGenerator realisticApi, IPluginPackage plugin) throws Exception;

}
