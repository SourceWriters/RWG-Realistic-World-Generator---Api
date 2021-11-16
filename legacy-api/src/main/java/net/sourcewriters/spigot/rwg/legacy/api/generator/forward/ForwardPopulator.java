package net.sourcewriters.spigot.rwg.legacy.api.generator.forward;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class ForwardPopulator extends BlockPopulator {

    private BlockPopulator[] populators = {};

    void setPopulators(final BlockPopulator[] populators) {
        this.populators = populators;
    }

    @Override
    public void populate(final World world, final Random random, final Chunk chunk) {
        final BlockPopulator[] populators = this.populators;
        for (final BlockPopulator populator : populators) {
            populator.populate(world, random, chunk);
        }
    }

}
