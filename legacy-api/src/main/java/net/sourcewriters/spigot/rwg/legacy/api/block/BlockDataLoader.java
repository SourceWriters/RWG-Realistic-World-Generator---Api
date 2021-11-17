package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.Objects;

import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class BlockDataLoader {

    protected final long id;
    protected final Plugin plugin;
    protected final String name;

    public BlockDataLoader(@NonNull final Plugin plugin, @NonNull final String name) {
        Objects.requireNonNull(plugin, "Plugin can't be null!");
        Objects.requireNonNull(name, "String name can't be null!");
        this.id = plugin.getName().hashCode() + name.hashCode() * 32;
        this.plugin = plugin;
        this.name = name;
    }

    public final long getId() {
        return id;
    }

    @NonNull
    public final Plugin getPlugin() {
        return plugin;
    }

    @NonNull
    public final String getName() {
        return name;
    }

    public abstract IBlockData load(@NonNull IBlockAccess access, @NonNull BlockState blockState, @NonNull BlockData blockData);

}
