package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.World;
import org.bukkit.block.Biome;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGBiome;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public class FullBiomeGrid implements IBiomeGrid {

    private final Biome[][] cache = new Biome[16][16];
    private final RWGBiome[][] output = new RWGBiome[16][16];
    private final World world;
    private final int cx, cz;

    private final INmsBiomeAccess access;

    public FullBiomeGrid(INmsBiomeAccess access, World world, int chunkX, int chunkZ) {
        this.access = access;
        this.world = world;
        this.cx = chunkX * 16;
        this.cz = chunkZ * 16;
    }

    public INmsBiomeAccess getNmsBiomeAccess() {
        return access;
    }

    @Override
    public Biome getBiome(int x, int z) {
        if (cache[x][z] != null) {
            return cache[x][z];
        }
        return cache[x][z] = access.getBiomeAt(world, x + cx, z + cz);
    }

    public Biome getBiome(int x, int y, int z) {
        return getBiome(x, z);
    }

    @Override
    public void setBiome(int x, int z, Biome biome) {
        cache[x][z] = biome;
    }

    public void setBiome(int x, int y, int z, Biome biome) {
        setBiome(x, z, biome);
    }

    public RWGBiome get(int x, int z) {
        return output[x][z];
    }

    public void set(int x, int z, RWGBiome biome) {
        output[x][z] = biome;
    }

}
