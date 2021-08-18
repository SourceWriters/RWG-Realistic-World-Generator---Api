package net.sourcewriters.spigot.rwg.legacy.api.generator;

import org.bukkit.World;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.SearchBiomeGrid;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public interface IRwgGenerator {

    SearchBiomeGrid getBiomeGridAt(INmsBiomeAccess access, World world, int x, int z);

}
