package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;

public final class DefaultMinecraftLoader extends BlockDataLoader {

    public DefaultMinecraftLoader(final Plugin plugin) {
        super(plugin, "Minecraft - All");
    }

    @Override
    public IBlockData load(final IBlockAccess access, final BlockState block, final BlockData blockData) {
        return access.dataOf(blockData).setConversionPossible(true);
    }

}
