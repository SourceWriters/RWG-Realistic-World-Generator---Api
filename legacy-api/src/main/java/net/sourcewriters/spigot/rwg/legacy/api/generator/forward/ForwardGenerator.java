package net.sourcewriters.spigot.rwg.legacy.api.generator.forward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public final class ForwardGenerator extends ChunkGenerator {

    private final long identifier = 345679324062398605L;

    private final ForwardPopulator populator = new ForwardPopulator();
    private ChunkGenerator generator;

    public ForwardGenerator(ChunkGenerator generator) {
        this.generator = generator;
    }

    public final void setGenerator(ChunkGenerator generator) {
        this.generator = generator;
    }

    public final ChunkGenerator getGenerator() {
        return generator;
    }

    public final void setPopulators(BlockPopulator[] populators) {
        populator.setPopulators(populators);
    }

    public final long getIdentifier() {
        return identifier;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        if (generator != null) {
            return generator.canSpawn(world, x, z);
        }
        return super.canSpawn(world, x, z);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        if (generator != null) {
            return generator.generateChunkData(world, random, x, z, biome);
        }
        return super.generateChunkData(world, random, x, z, biome);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> list = new ArrayList<>();
        list.add(populator);
        if (generator != null) {
            List<BlockPopulator> populators = generator.getDefaultPopulators(world);
            populator.setPopulators(
                populators == null ? new BlockPopulator[0] : populators.stream().filter(obj -> obj != null).toArray(BlockPopulator[]::new));
        }
        return list;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        if (generator != null) {
            return generator.getFixedSpawnLocation(world, random);
        }
        return super.getFixedSpawnLocation(world, random);
    }

    @Override
    public boolean isParallelCapable() {
        if (generator != null) {
            return generator.isParallelCapable();
        }
        return super.isParallelCapable();
    }

    @Override
    public boolean shouldGenerateCaves() {
        if (generator != null) {
            return generator.shouldGenerateCaves();
        }
        return super.shouldGenerateCaves();
    }

    @Override
    public boolean shouldGenerateDecorations() {
        if (generator != null) {
            return generator.shouldGenerateDecorations();
        }
        return super.shouldGenerateDecorations();
    }

    @Override
    public boolean shouldGenerateMobs() {
        if (generator != null) {
            return generator.shouldGenerateMobs();
        }
        return super.shouldGenerateMobs();
    }

    @Override
    public boolean shouldGenerateStructures() {
        if (generator != null) {
            return generator.shouldGenerateStructures();
        }
        return super.shouldGenerateStructures();
    }

}
