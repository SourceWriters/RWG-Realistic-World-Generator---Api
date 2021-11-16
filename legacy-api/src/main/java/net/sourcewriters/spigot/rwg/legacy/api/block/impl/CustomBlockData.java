package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

public class CustomBlockData extends BaseBlockData {

    private static final BlockData AIR = Bukkit.createBlockData(Material.AIR);

    private final String namespace;
    private final String id;
    private final String key;

    public CustomBlockData(@NonNull final String namespace, @NonNull final String id) {
        this.namespace = Objects.requireNonNull(namespace, "String namespace can't be null!");
        this.id = Objects.requireNonNull(id, "String id can't be null!");
        this.key = namespace + ":" + id;
        setConversionPossible(false);
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

    @Override
    protected String dataString() {
        return key;
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
