package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public final class ShiftedRwgGrid extends RwgGrid {

    public ShiftedRwgGrid(final IBiomeAccess biome, final INmsBiomeAccess nmsBiome, final World world, final BiomeGrid grid,
        final int chunkX, final int chunkZ) {
        super(biome, nmsBiome, world, grid, chunkX, chunkZ);
    }

    @Override
    protected Biome internalGet(final int x, final int z) {
        if (grid == null) {
            return nmsBiome.getBiomeAt(world, x + cx, z + cz);
        }
        return biome.getBiome(grid, x, z);
    }

    @Override
    public boolean needsRemap() {
        return true;
    }

}
