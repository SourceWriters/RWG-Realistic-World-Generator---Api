package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilityAddon {

    private final boolean external;
    private final Plugin owner;
    private final IPluginPackage plugin;
    
    private final String id;

    public CompatibilityAddon(ICompatibilityManager manager, Plugin owner, IPluginPackage target) {
        Objects.requireNonNull(manager, "ICompatibilityManager can't be null!");
        Objects.requireNonNull(owner, "Plugin can't be null!");
        Objects.requireNonNull(target, "Target plugin name can't be null!");
        this.external = manager.isExternal(owner);
        this.plugin = target;
        this.owner = owner;
        this.id = owner.getName() + '-' + target.getName();
    }

    public final boolean isExternal() {
        return external;
    }
    
    @NonNull
    public final String getId() {
        return id;
    }

    @NonNull
    public final Plugin getOwner() {
        return owner;
    }

    @NonNull
    public final IPluginPackage getTarget() {
        return plugin;
    }

    public final boolean enable(RealisticWorldGenerator realisticApi) {
        try {
            onEnable(realisticApi, plugin);
            return true;
        } catch (Exception exception) {
            realisticApi.getLogger().log(LogTypeId.ERROR, "Failed to enable CompatibilityAddon of '" + owner.getName() + "' for '" + plugin.getName() + "'!");
            realisticApi.getLogger().log(LogTypeId.ERROR, exception);
            return false;
        }
    }

    public final boolean disable(RealisticWorldGenerator realisticApi) {
        try {
            onDisable(realisticApi, plugin);
            return true;
        } catch (Exception exception) {
            realisticApi.getLogger().log(LogTypeId.ERROR, "Failed to disable CompatibilityAddon of '" + owner.getName() + "' for '" + plugin.getName() + "'!");
            realisticApi.getLogger().log(LogTypeId.ERROR, exception);
            return false;
        }
    }

    protected abstract void onEnable(RealisticWorldGenerator realisticApi, IPluginPackage plugin) throws Exception;

    protected abstract void onDisable(RealisticWorldGenerator realisticApi, IPluginPackage plugin) throws Exception;

}
