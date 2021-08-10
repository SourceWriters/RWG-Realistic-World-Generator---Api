package net.sourcewriters.spigot.rwg.legacy.api.block;

import org.bukkit.Location;
import org.bukkit.block.Block;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBlockDataLoaderManager {

    boolean register(@NonNull BlockDataLoader loader);

    boolean unregister(long id);

    boolean has(long id);

    BlockDataLoader get(long id);
    
    int getPosition(long id);

    IBlockData load(@NonNull Location location);

    IBlockData load(@NonNull Block block);

}
