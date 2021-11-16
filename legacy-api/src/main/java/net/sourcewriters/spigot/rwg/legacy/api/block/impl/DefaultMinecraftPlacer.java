package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public class DefaultMinecraftPlacer extends BlockDataPlacer {

    public DefaultMinecraftPlacer(final Plugin plugin) {
        super(plugin, "minecraft");
    }

    @Override
    public boolean placeBlock(final Location location, final Block block, final IBlockData data, final RandomNumberGenerator random,
        final MinecraftVersion minecraft, final ServerVersion server) {
        final BlockData bukkit = data.asBukkit();
        if (bukkit == null) {
            return false;
        }
        block.setBlockData(bukkit);
        return true;
    }

}
