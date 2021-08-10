package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;

public final class BiomeAccessImpl implements IBiomeAccess {

    @Override
    public final Biome getBiome(BiomeGrid grid, int x, int y, int z) {
        return grid.getBiome(x, y, z);
    }

    @Override
    public final void setBiome(BiomeGrid grid, int x, int y, int z, Biome biome) {
        grid.setBiome(x, y, z, biome);
    }

    @Override
    public final Biome getBiome(World world, int x, int y, int z) {
        return world.getBiome(x, y, z);
    }

    @Override
    public final void setBiome(World world, int x, int y, int z, Biome biome) {
        world.setBiome(x, y, z, biome);
    }

}
