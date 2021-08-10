package net.sourcewriters.spigot.rwg.legacy.api.version.nms;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;

import net.sourcewriters.spigot.rwg.legacy.api.access.IRwgGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.util.Checks;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.SearchBiomeGrid;

public interface INmsBiomeAccess {

    default SearchBiomeGrid getBiomeGrid(Chunk chunk) {
        return getBiomeGridAt(chunk.getWorld(), chunk.getX(), chunk.getZ());
    }

    default SearchBiomeGrid getBiomeGridAt(World world, int x, int z) {
        if (Checks.isRwg(world)) {
            return null;
        }
        return ((IRwgGenerator) world.getGenerator()).getBiomeGridAt(this, world, x, z);
    }

    @NonNull
    default Biome getBiomeAt(@NonNull World world, int x, int z) {
        return getBiomeAt(world, x, 0, z);
    }

    @NonNull
    Biome getBiomeAt(@NonNull World world, int x, int y, int z);

}
