package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.block.Block;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataPlacerManager;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public class BlockDataPlacerManagerImpl implements IBlockDataPlacerManager {

    private final ConcurrentHashMap<Long, BlockDataPlacer> placers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<Long>> order = new ConcurrentHashMap<>();

    private final ILogger logger;

    public BlockDataPlacerManagerImpl(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public boolean register(@NonNull BlockDataPlacer placer) {
        Objects.requireNonNull(placer, "BlockPlacer can't be null!");
        if (has(placer.getId())) {
            return false;
        }
        placers.put(placer.getId(), placer);
        order.computeIfAbsent(placer.getNamespace(), (ignore) -> new ArrayList<>()).add(placer.getId());
        return true;
    }

    @Override
    public boolean unregister(long id) {
        BlockDataPlacer placer = placers.remove(id);
        if (placer == null) {
            return false;
        }
        List<Long> list = order.get(placer.getNamespace());
        list.remove(id);
        if (list.isEmpty()) {
            order.remove(placer.getNamespace());
        }
        return true;
    }

    @Override
    public boolean has(long id) {
        return placers.contains(id);
    }

    @Override
    public BlockDataPlacer get(long id) {
        return placers.get(id);
    }

    @Override
    public int getPosition(long id) {
        BlockDataPlacer placer = get(id);
        if (placer == null) {
            return -1;
        }
        List<Long> list = order.get(placer.getNamespace());
        return list.size() - list.indexOf(id);
    }

    @Override
    public BlockDataPlacer getOwner(IBlockData data) {
        Objects.requireNonNull(data, "IBlockData can't be null!");
        String namespace = data.getNamespace();
        List<Long> list = order.get(namespace);
        if (list == null) {
            return null;
        }
        int size = list.size();
        for (int index = size - 1; index >= 0; index--) {
            BlockDataPlacer placer = placers.get(list.get(index));
            if (!placer.owns(data)) {
                continue;
            }
            return placer;
        }
        return null;
    }

    @Override
    public boolean setBlock(@NonNull Block block, @NonNull IBlockData data, @NonNull RandomNumberGenerator random) {
        Objects.requireNonNull(block, "Block can't be null!");
        Objects.requireNonNull(data, "IBlockData can't be null!");
        Objects.requireNonNull(random, "RandomNumberGenerator can't be null!");
        String namespace = data.getNamespace();
        List<Long> list = order.get(namespace);
        if (list == null) {
            logger.log(LogTypeId.WARNING, "Can't setBlock for namespace '" + namespace + "', no BlockPlacer available!");
            return false;
        }
        MinecraftVersion minecraft = Versions.getMinecraft();
        ServerVersion server = Versions.getServer();
        int size = list.size();
        for (int index = size - 1; index >= 0; index--) {
            BlockDataPlacer placer = placers.get(list.get(index));
            if (!placer.owns(data)) {
                continue;
            }
            boolean output = placer.placeBlock(block.getLocation(), block, data, random, minecraft, server);
            if (output) {
                return output;
            }
        }
        return false;
    }

}
