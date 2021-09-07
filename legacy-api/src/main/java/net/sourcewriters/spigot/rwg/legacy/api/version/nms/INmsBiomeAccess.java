package net.sourcewriters.spigot.rwg.legacy.api.version.nms;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;

import net.sourcewriters.spigot.rwg.legacy.api.generator.IRwgGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.generator.forward.ForwardHelper;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid.RwgGrid;

public interface INmsBiomeAccess {

    default RwgGrid getBiomeGrid(Chunk chunk) {
        return getBiomeGridAt(chunk.getWorld(), chunk.getX(), chunk.getZ());
    }

    default RwgGrid getBiomeGridAt(World world, int x, int z) {
        IRwgGenerator generator = ForwardHelper.get(world);
        if (generator == null) {
            return null;
        }
        return generator.getBiomeGridAt(this, world, x, z);
    }

    @NonNull
    default Biome getBiomeAt(@NonNull World world, int x, int z) {
        return getBiomeAt(world, x, 0, z);
    }

    @NonNull
    Biome getBiomeAt(@NonNull World world, int x, int y, int z);

}
