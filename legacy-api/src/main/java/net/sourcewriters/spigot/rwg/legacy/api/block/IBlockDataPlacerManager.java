package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBlockDataPlacerManager {
    
    boolean register(@NonNull BlockDataPlacer handler);

    boolean unregister(long id);

    boolean has(long id);

    BlockDataPlacer get(long id);
    
    int getPosition(long id);

    default boolean setBlock(@NonNull Location location, @NonNull IBlockData data, @NonNull RandomNumberGenerator random) {
        return setBlock(Objects.requireNonNull(location, "Location can't be null").getBlock(), data, random);
    }

    boolean setBlock(@NonNull Block block, @NonNull IBlockData data, @NonNull RandomNumberGenerator random);

    boolean injectData(@NonNull IBlockData data);
    
}
