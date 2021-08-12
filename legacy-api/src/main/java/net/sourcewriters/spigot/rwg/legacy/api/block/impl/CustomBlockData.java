package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;
import net.sourcewriters.spigot.rwg.legacy.api.util.data.JsonIO;

public class CustomBlockData extends BaseBlockData {
    
    private static final BlockData AIR = Bukkit.createBlockData(Material.AIR);

    private final String namespace;
    private final String id;
    private final String key;

    public CustomBlockData(@NonNull String namespace, @NonNull String id) {
        this.namespace = Objects.requireNonNull(namespace, "String namespace can't be null!");
        this.id = Objects.requireNonNull(id, "String id can't be null!");
        this.key = namespace + ":" + id;
    }

    @NonNull
    @Override
    public final String getNamespace() {
        return namespace;
    }

    @NonNull
    @Override
    public final String getId() {
        return id;
    }

    @NonNull
    @Override
    public final String getKey() {
        return key;
    }

    @NonNull
    @Override
    public Material getMaterial() {
        return Material.AIR;
    }

    @NonNull
    @Override
    public final String asString() {
        return key + JsonIO.toString(properties);
    }

    @NonNull
    @Override
    public BlockData asBukkit() {
        return AIR;
    }

    @NonNull
    @Unsafe(status = UnsafeStatus.MISSING_DATA, useable = true)
    @Override
    public final BlockStateEditor asEditor() {
        return BlockStateEditor.of(key);
    }

}
