package net.sourcewriters.spigot.rwg.legacy.api.schematic.update;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.schematic.ISchematic;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class SchematicUpdate<T extends ISchematic> {

    protected final long id;
    protected final Plugin plugin;

    protected final Class<T> type;
    protected final boolean preLoad;
    protected final MinecraftVersion version;

    public SchematicUpdate(Plugin plugin, MinecraftVersion version, Class<T> type) {
        this(plugin, version, type, false);
    }

    public SchematicUpdate(Plugin plugin, MinecraftVersion version, Class<T> type, boolean preLoad) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin can't be null!");
        this.version = Objects.requireNonNull(version, "MinecraftVersion schematic version can't be null!");;
        this.type = Objects.requireNonNull(type, "Class type can't be null!");;
        this.preLoad = preLoad;
        this.id = ((plugin.getName().hashCode() + (type.hashCode() * 16) + (version.asSpecialHash() * 32)) << 1) | (preLoad ? 0 : 1);
    }
    
    public final long getId() {
        return id;
    }

    public final Class<T> getType() {
        return type;
    }

    public final boolean isPreLoad() {
        return preLoad;
    }

    public final MinecraftVersion getVersion() {
        return version;
    }

    public final boolean update(RealisticWorldGenerator api, T schematic) {
        try {
            updateInternal(api, schematic);
            return true;
        } catch (Exception exp) {
            if (api.getLogger().getState().extendedInfo()) {
                api.getLogger().log(LogTypeId.WARNING, exp);
            }
            return false;
        }
    }

    protected abstract void updateInternal(RealisticWorldGenerator api, T schematic) throws Exception;

}
