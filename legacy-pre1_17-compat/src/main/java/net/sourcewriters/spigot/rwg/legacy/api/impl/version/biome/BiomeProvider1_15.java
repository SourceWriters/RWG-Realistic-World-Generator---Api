package net.sourcewriters.spigot.rwg.legacy.api.impl.version.biome;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public final class BiomeProvider1_15 extends BiomeProvider {

    @Override
    public Biome getBiome(BiomeGrid grid, int x, int y, int z) {
        return grid.getBiome(x, y, z);
    }

    @Override
    public void setBiome(BiomeGrid grid, int x, int y, int z, Biome biome) {
        grid.setBiome(x, y, z, biome);
    }

    @Override
    public Biome getBiome(World world, int x, int y, int z) {
        return world.getBiome(x, y, z);
    }

    @Override
    public void setBiome(World world, int x, int y, int z, Biome biome) {
        world.setBiome(x, y, z, biome);
    }

}
