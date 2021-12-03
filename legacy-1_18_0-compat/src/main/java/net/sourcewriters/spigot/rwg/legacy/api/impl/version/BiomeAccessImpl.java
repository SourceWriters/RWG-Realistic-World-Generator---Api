package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;

@SuppressWarnings("deprecation")
public final class BiomeAccessImpl implements IBiomeAccess {

    @Override
    public Biome getBiome(final BiomeGrid grid, final int x, final int y, final int z) {
        return grid.getBiome(x, y, z);
    }
    
    @Override
    public void setBiome(final BiomeGrid grid, final int x, final int y, final int z, final Biome biome) {
        grid.setBiome(x, y, z, biome);
    }

    @Override
    public Biome getBiome(final World world, final int x, final int y, final int z) {
        return world.getBiome(x, y, z);
    }

    @Override
    public void setBiome(final World world, final int x, final int y, final int z, final Biome biome) {
        world.setBiome(x, y, z, biome);
    }

}
