package net.sourcewriters.spigot.rwg.legacy.api.generator;

import org.bukkit.World;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.grid.IBiomeGrid;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public interface IRwgGenerator {

    IBiomeGrid getBiomeGridAt(INmsBiomeAccess access, World world, int x, int z);

}
