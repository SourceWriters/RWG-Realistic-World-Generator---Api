package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public final class FullRwgGrid extends RwgGrid {

    public FullRwgGrid(IBiomeAccess biome, INmsBiomeAccess nmsBiome, World world, BiomeGrid grid, int chunkX, int chunkZ) {
        super(biome, nmsBiome, world, grid, chunkX, chunkZ);
    }

    @Override
    protected Biome internalGet(int x, int z) {
        if(grid == null) {
            return nmsBiome.getBiomeAt(world, x + cx, z + cz);
        }
        return biome.getBiome(grid, x, z);
    }

    @Override
    public boolean needsRemap() {
        return false;
    }
    
}
