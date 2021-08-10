package net.sourcewriters.spigot.rwg.legacy.api.impl.version.biome;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class BiomeProvider {
    
    public abstract Biome getBiome(@NonNull BiomeGrid grid, int x, int y, int z);

    public abstract void setBiome(@NonNull BiomeGrid grid, int x, int y, int z, Biome biome);

    public abstract Biome getBiome(@NonNull World world, int x, int y, int z);

    public abstract void setBiome(@NonNull World world, int x, int y, int z, Biome biome);

}
