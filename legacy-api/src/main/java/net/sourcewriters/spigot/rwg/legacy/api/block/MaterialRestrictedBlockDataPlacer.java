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

    public MaterialRestrictedBlockDataPlacer(@NonNull final Plugin plugin, @NonNull final String namespace) {
        super(plugin, namespace);
    }

    protected final ArrayList<Material> materials = new ArrayList<>();
    protected boolean whitelist = false;

    public final boolean isWhitelisted(final Material material) {
        return materials.contains(material) == whitelist;
    }

    protected boolean internalOwns(@NonNull final IBlockData data) {
        return true;
    }

    protected abstract boolean internalPlace(@NonNull Location location, @NonNull Block block, @NonNull IBlockData data,
        @NonNull RandomNumberGenerator random, @NonNull MinecraftVersion minecraft, @NonNull ServerVersion server);

    @Override
    public final boolean owns(@NonNull final IBlockData data) {
        if (!isWhitelisted(data.getMaterial())) {
            return false;
        }
        return internalOwns(data);
    }

    @Override
    public final boolean placeBlock(@NonNull final Location location, @NonNull final Block block, @NonNull final IBlockData data,
        @NonNull final RandomNumberGenerator random, @NonNull final MinecraftVersion minecraft, @NonNull final ServerVersion server) {
        if (!isWhitelisted(data.getMaterial())) {
            return false;
        }
        return internalPlace(location, block, data, random, minecraft, server);
    }

}
