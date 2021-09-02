package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGBiome;

public interface IBiomeGrid extends BiomeGrid {
    
    RWGBiome get(int x, int z);
    
    void set(int x, int z, RWGBiome biome);

}
