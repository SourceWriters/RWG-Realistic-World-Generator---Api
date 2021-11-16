package net.sourcewriters.spigot.rwg.legacy.api.version;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBiomeAccess {

    default Biome getBiome(@NonNull final BiomeGrid grid, final int x, final int z) {
        return getBiome(grid, x, 0, z);
    }

    default void setBiome(@NonNull final BiomeGrid grid, final int x, final int z, final Biome biome) {
        setBiome(grid, x, 0, z, biome);
    }

    default Biome getBiome(@NonNull final World world, final int x, final int z) {
        return getBiome(world, x, 0, z);
    }

    default void setBiome(@NonNull final World world, final int x, final int z, final Biome biome) {
        setBiome(world, x, 0, z, biome);
    }

    Biome getBiome(@NonNull BiomeGrid grid, int x, int y, int z);

    void setBiome(@NonNull BiomeGrid grid, int x, int y, int z, Biome biome);

    Biome getBiome(@NonNull World world, int x, int y, int z);

    void setBiome(@NonNull World world, int x, int y, int z, Biome biome);

}
