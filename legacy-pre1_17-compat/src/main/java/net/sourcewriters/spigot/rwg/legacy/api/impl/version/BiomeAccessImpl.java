package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import net.sourcewriters.spigot.rwg.legacy.api.impl.version.biome.BiomeProvider;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.InstanceBuilder;
import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;

public final class BiomeAccessImpl implements IBiomeAccess {

    private final BiomeProvider provider;

    public BiomeAccessImpl() {
        this.provider = InstanceBuilder.buildBelow(BiomeProvider.class)
            .orElseThrow(() -> new IllegalStateException("Unable to find BiomeProvider!"));
    }

    @Override
    public final Biome getBiome(BiomeGrid grid, int x, int y, int z) {
        return provider.getBiome(grid, x, y, z);
    }

    @Override
    public final void setBiome(BiomeGrid grid, int x, int y, int z, Biome biome) {
        provider.setBiome(grid, x, y, z, biome);
    }

    @Override
    public final Biome getBiome(World world, int x, int y, int z) {
        return provider.getBiome(world, x, y, z);
    }

    @Override
    public final void setBiome(World world, int x, int y, int z, Biome biome) {
        provider.setBiome(world, x, y, z, biome);
    }

}
