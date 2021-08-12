package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class MaterialRestrictedBlockDataLoader extends BlockDataLoader {

    public MaterialRestrictedBlockDataLoader(@NonNull Plugin plugin, @NonNull String name) {
        super(plugin, name);
    }

    protected final ArrayList<Material> materials = new ArrayList<>();
    protected boolean whitelist = false;

    public final boolean isWhitelisted(Material material) {
        return materials.contains(material) ? whitelist : !whitelist;
    }

    protected abstract IBlockData internalLoad(@NonNull IBlockAccess access, @NonNull Block block, @NonNull BlockData blockData);

    public final IBlockData load(@NonNull IBlockAccess access, @NonNull Block block, @NonNull BlockData blockData) {
        if (!isWhitelisted(blockData.getMaterial())) {
            return null;
        }
        return internalLoad(access, block, blockData);
    }

}
