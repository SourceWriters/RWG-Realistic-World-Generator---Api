package net.sourcewriters.spigot.rwg.legacy.api.generator.forward;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class ForwardPopulator extends BlockPopulator {

    private BlockPopulator[] populators = new BlockPopulator[0];

    void setPopulators(BlockPopulator[] populators) {
        this.populators = populators;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        BlockPopulator[] populators = this.populators;
        for (BlockPopulator populator : populators) {
            populator.populate(world, random, chunk);
        }
    }

}
