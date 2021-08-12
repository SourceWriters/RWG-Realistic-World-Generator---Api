package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public abstract class MaterialRestrictedBlockDataPlacer extends BlockDataPlacer {

    public MaterialRestrictedBlockDataPlacer(@NonNull Plugin plugin, @NonNull String namespace) {
        super(plugin, namespace);
    }

    protected final ArrayList<Material> materials = new ArrayList<>();
    protected boolean whitelist = false;

    public final boolean isWhitelisted(Material material) {
        return materials.contains(material) ? whitelist : !whitelist;
    }

    protected boolean internalOwns(@NonNull IBlockData data) {
        return true;
    }

    protected abstract boolean internalPlace(@NonNull Location location, @NonNull Block block, @NonNull IBlockData data,
        @NonNull RandomNumberGenerator random, @NonNull MinecraftVersion minecraft, @NonNull ServerVersion server);

    public final boolean owns(@NonNull IBlockData data) {
        if (!isWhitelisted(data.getMaterial())) {
            return false;
        }
        return internalOwns(data);
    }

    public final boolean placeBlock(@NonNull Location location, @NonNull Block block, @NonNull IBlockData data,
        @NonNull RandomNumberGenerator random, @NonNull MinecraftVersion minecraft, @NonNull ServerVersion server) {
        if (!isWhitelisted(data.getMaterial())) {
            return false;
        }
        return internalPlace(location, block, data, random, minecraft, server);
    }

}
