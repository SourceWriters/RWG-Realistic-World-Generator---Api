package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class BlockDataParser {
    
    private static final AtomicLong GLOBAL_ID = new AtomicLong(0);

    protected final long id;
    protected final Plugin plugin;
    protected final String namespace;

    public BlockDataParser(@NonNull Plugin plugin, @NonNull String namespace) {
        Objects.requireNonNull(plugin, "Plugin can't be null!");
        Objects.requireNonNull(namespace, "String namespace can't be null!");
        this.id = GLOBAL_ID.getAndIncrement();
        this.plugin = plugin;
        this.namespace = namespace;
    }

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

    public abstract IBlockData parse(@NonNull IBlockAccess access, @NonNull NbtCompound compound);

}
