package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public abstract class BlockDataPlacer {
    
    private static final AtomicLong GLOBAL_ID = new AtomicLong(0);

    protected final long id;

    protected final String namespace;
    protected final Plugin plugin;

    public BlockDataPlacer(@NonNull Plugin plugin, @NonNull String namespace) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin can't be null!");
        Objects.requireNonNull(namespace, "String namespace can't be null!");
        Preconditions.checkArgument(!namespace.isBlank(), "String namespace can't be empty!");
        this.namespace = namespace;
        this.id = GLOBAL_ID.getAndIncrement();
    }

    @NonNull
    public final long getId() {
        return id;
    }

    @NonNull
    public final Plugin getPlugin() {
        return plugin;
    }

    @NonNull
    public final String getNamespace() {
        return namespace;
    }

    public boolean owns(@NonNull IBlockData data) {
        return true;
    }

    public abstract boolean placeBlock(@NonNull Location location, @NonNull Block block, @NonNull IBlockData data,
        @NonNull RandomNumberGenerator random, @NonNull MinecraftVersion minecraft, @NonNull ServerVersion server);

}
