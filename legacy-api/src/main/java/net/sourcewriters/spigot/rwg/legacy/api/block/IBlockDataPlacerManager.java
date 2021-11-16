package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.Objects;
import java.util.function.Supplier;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBlockDataPlacerManager {

    boolean register(@NonNull BlockDataPlacer placer);

    default boolean register(final boolean expression, @NonNull final Supplier<BlockDataPlacer> placer) {
        if (!expression) {
            return false;
        }
        return register(placer.get());
    }

    boolean unregister(long id);

    boolean has(long id);

    BlockDataPlacer get(long id);

    int getPosition(long id);

    BlockDataPlacer getOwner(@NonNull IBlockData data);

    default boolean setBlock(@NonNull final Location location, @NonNull final IBlockData data,
        @NonNull final RandomNumberGenerator random) {
        return setBlock(Objects.requireNonNull(location, "Location can't be null").getBlock(), data, random);
    }

    boolean setBlock(@NonNull Block block, @NonNull IBlockData data, @NonNull RandomNumberGenerator random);

}
