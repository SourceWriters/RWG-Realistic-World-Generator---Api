package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataLoaderManager;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public class BlockDataLoaderManagerImpl implements IBlockDataLoaderManager {

    private final ConcurrentHashMap<Long, BlockDataLoader> loaders = new ConcurrentHashMap<>();
    private final ArrayList<Long> order = new ArrayList<>();

    private final ILogger logger;
    private final IBlockAccess access;

    public BlockDataLoaderManagerImpl(final ILogger logger, final IBlockAccess access) {
        this.logger = logger;
        this.access = access;
    }

    @Override
    public boolean register(final BlockDataLoader loader) {
        Objects.requireNonNull(loader, "BlockDataLoader can't be null!");
        if (has(loader.getId())) {
            return false;
        }
        loaders.put(loader.getId(), loader);
        order.add(loader.getId());
        return true;
    }

    @Override
    public boolean unregister(final long id) {
        order.remove(id);
        return loaders.remove(id) != null;
    }

    @Override
    public boolean has(final long id) {
        return order.contains(id);
    }

    @Override
    public BlockDataLoader get(final long id) {
        return loaders.get(id);
    }

    @Override
    public int getPosition(final long id) {
        return order.size() - order.indexOf(id);
    }

    @Override
    public IBlockData load(@NonNull final Location location) {
        Objects.requireNonNull(location, "Location can't be null!");
        if (Bukkit.isPrimaryThread()) {
            try {
                return load(location.getBlock());
            } catch (final Exception exp) {
                logger.log(LogTypeId.WARNING, "Failed to load block");
                logger.log(LogTypeId.WARNING, exp);
                return null;
            }
        }
        try {
            return load(location.getBlock());
        } catch (final Exception exp) {
            logger.log(LogTypeId.WARNING, "Failed to load block asynchronously to main thread");
            logger.log(LogTypeId.WARNING, exp);
            return null;
        }
    }

    @Override
    public IBlockData load(@NonNull final Block block) {
        Objects.requireNonNull(block, "Block can't be null!");
        if (loaders.isEmpty()) {
            logger.log(LogTypeId.ERROR, "Can't load blocks without BlockDataLoader's!");
            return null;
        }
        final BlockData data = block.getBlockData();
        final int size = order.size();
        for (int index = size - 1; index >= 0; index--) {
            final IBlockData output = loaders.get(order.get(index)).load(access, block, data);
            if (output != null) {
                return output;
            }
        }
        return null;
    }

}
