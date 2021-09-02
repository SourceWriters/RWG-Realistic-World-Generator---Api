package net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid;

import org.bukkit.World;
import org.bukkit.block.Biome;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGBiome;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public class ShiftedBiomeGrid implements IBiomeGrid {

    private final Biome[][] cache = new Biome[4][4];
    private final RWGBiome[][] output = new RWGBiome[4][4];
    private final World world;
    private final int cx, cz;

    private final INmsBiomeAccess access;

    public ShiftedBiomeGrid(INmsBiomeAccess access, World world, int chunkX, int chunkZ) {
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
        int sx = x >> 2;
        int sz = z >> 2;
        if (cache[sx][sz] != null) {
            return cache[sx][sz];
        }
        return cache[sx][sz] = access.getBiomeAt(world, x + cx, z + cz);
    }

    public Biome getBiome(int x, int y, int z) {
        return getBiome(x, z);
    }

    @Override
    public void setBiome(int x, int z, Biome biome) {
        cache[x >> 2][z >> 2] = biome;
    }

    public void setBiome(int x, int y, int z, Biome biome) {
        setBiome(x, z, biome);
    }

    public RWGBiome get(int x, int z) {
        return output[x >> 2][z >> 2];
    }

    public void set(int x, int z, RWGBiome biome) {
        output[x >> 2][z >> 2] = biome;
    }

}
