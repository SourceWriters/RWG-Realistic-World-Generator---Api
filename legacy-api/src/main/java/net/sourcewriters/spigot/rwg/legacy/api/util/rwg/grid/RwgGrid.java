package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGBiome;
import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public abstract class RwgGrid {

    protected final Biome[][] cache = new Biome[16][16];
    protected final RWGBiome[][] output = new RWGBiome[16][16];
    protected final World world;
    protected final int cx, cz;

    protected final INmsBiomeAccess nmsBiome;
    protected final IBiomeAccess biome;

    protected final BiomeGrid grid;

    public RwgGrid(final IBiomeAccess biome, final INmsBiomeAccess nmsBiome, final World world, final BiomeGrid grid, final int chunkX,
        final int chunkZ) {
        this.nmsBiome = nmsBiome;
        this.biome = biome;
        this.world = world;
        this.cx = chunkX * 16;
        this.cz = chunkZ * 16;
        this.grid = grid;
    }

    public INmsBiomeAccess getNmsBiomeAccess() {
        return nmsBiome;
    }

    public IBiomeAccess getBiomeAccess() {
        return biome;
    }

    protected abstract Biome internalGet(int x, int z);

    public abstract boolean needsRemap();

    public Biome getBukkit(final int x, final int z) {
        if (cache[x][z] != null) {
            return cache[x][z];
        }
        return cache[x][z] = internalGet(x, z);
    }

    public void setBukkit(final int x, final int z, final Biome biome) {
        cache[x][z] = biome;
    }

    public RWGBiome get(final int x, final int z) {
        return output[x][z];
    }

    public void set(final int x, final int z, final RWGBiome biome) {
        output[x][z] = biome;
    }

}
