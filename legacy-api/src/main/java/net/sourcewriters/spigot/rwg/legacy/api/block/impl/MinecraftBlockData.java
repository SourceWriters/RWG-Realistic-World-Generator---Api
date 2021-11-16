package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public class MinecraftBlockData extends BaseBlockData {

    private final String namespace;
    private final String id;
    private final String key;

    private final BlockData data;

    public MinecraftBlockData(final BlockData data) {
        this.data = Objects.requireNonNull(data, "BlockData data can't be null!");
        final String[] tmp = data.getAsString().split("\\[", 2)[0].split(":", 2);
        this.namespace = tmp[0];
        this.id = tmp[1];
        this.key = namespace + ":" + id;
    }

    @NonNull
    @Override
    public final String getNamespace() {
        return namespace;
    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public String getKey() {
        return key;
    }

    @NonNull
    @Override
    public Material getMaterial() {
        return data.getMaterial();
    }

    @NonNull
    @Override
    public BlockData asBukkit() {
        return data;
    }

    @Override
    protected String dataString() {
        return data.getAsString();
    }

    @NonNull
    @Override
    public final BlockStateEditor asEditor() {
        return BlockStateEditor.of(asString());
    }

}
