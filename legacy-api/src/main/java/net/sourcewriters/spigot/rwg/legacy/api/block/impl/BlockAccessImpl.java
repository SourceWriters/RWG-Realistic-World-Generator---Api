package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.Objects;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import com.syntaxphoenix.syntaxapi.json.utils.Tracker;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataLoaderManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataParserManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataPlacerManager;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.CallerSensitive;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.data.JsonIO;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGMaterial;
import net.sourcewriters.spigot.rwg.legacy.api.version.IConversionAccess;

public class BlockAccessImpl implements IBlockAccess {

    private final ILogger logger;
    private final IConversionAccess access;
    private final IDataFixHandler fixHandler;

    private final BlockDataLoaderManagerImpl loaderManager;
    private final BlockDataParserManagerImpl parserManager;
    private final BlockDataPlacerManagerImpl placerManager;

    public BlockAccessImpl(final ILogger logger, final IConversionAccess access, final IDataFixHandler fixHandler) {
        this.logger = logger;
        this.access = access;
        this.fixHandler = fixHandler;
        this.loaderManager = new BlockDataLoaderManagerImpl(logger, this);
        this.parserManager = new BlockDataParserManagerImpl(logger, this);
        this.placerManager = new BlockDataPlacerManagerImpl(logger);
    }

    @NonNull
    @Override
    public IBlockDataLoaderManager getLoaderManager() {
        return loaderManager;
    }

    @NonNull
    @Override
    public IBlockDataParserManager getParserManager() {
        return parserManager;
    }

    @NonNull
    @Override
    public IBlockDataPlacerManager getPlacerManager() {
        return placerManager;
    }

    @CallerSensitive
    @Override
    public IBlockData dataOf(@NonNull final Block block) {
        Objects.requireNonNull(block, "Block can't be null!");
        final Optional<Class<?>> clazz = Tracker.getCallerClass();
        if (clazz.isPresent() && BlockDataLoader.class.isAssignableFrom(clazz.get())) {
            logger.log(LogTypeId.WARNING,
                "BlockDataLoader (" + clazz.get().getName() + ") is calling dataOf(Block), this could cause an infinite loop!");
        }
        return loaderManager.load(block);
    }

    @Override
    public IBlockData dataOf(BlockState blockState) {
        Objects.requireNonNull(blockState, "BlockState can't be null!");
        final Optional<Class<?>> clazz = Tracker.getCallerClass();
        if (clazz.isPresent() && BlockDataLoader.class.isAssignableFrom(clazz.get())) {
            logger.log(LogTypeId.WARNING,
                "BlockDataLoader (" + clazz.get().getName() + ") is calling dataOf(Block), this could cause an infinite loop!");
        }
        return loaderManager.load(blockState);
    }

    @CallerSensitive
    @Override
    public IBlockData dataOf(@NonNull final String rawData) {
        Objects.requireNonNull(rawData, "String rawData can't be null!");
        final BlockStateEditor editor = BlockStateEditor.of(rawData);
        if ("minecraft".equalsIgnoreCase(editor.getNamespace())) {
            fixHandler.apply(editor);
            try {
                final IBlockData data = dataOf(Bukkit.createBlockData(editor.asBlockData()));
                if (!editor.hasProperties()) {
                    return data;
                }
                data.getProperties().set(JsonIO.fromString(editor.getProperties()).asArray());
                return data;
            } catch (final IllegalArgumentException exp) {
                logger.log(LogTypeId.WARNING, "Unable to parse IBlockData of '" + rawData + "'!");
                logger.log(LogTypeId.WARNING, exp);
                return null;
            }
        }
        final Optional<Class<?>> clazz = Tracker.getCallerClass();
        if (clazz.isPresent() && BlockDataParser.class.isAssignableFrom(clazz.get())) {
            logger.log(LogTypeId.WARNING, "BlockDataParser (" + clazz.get().getName()
                + ") is calling dataOf(String) with custom namespace, this could cause an infinite loop!");
        }
        return parserManager.parse(editor);
    }

    @Override
    public IBlockData dataOf(@NonNull final BlockData data) {
        Objects.requireNonNull(data, "Block can't be null!");
        return new MinecraftBlockData(data);
    }

    @Override
    public IBlockData dataOf(@NonNull final Material material) {
        Objects.requireNonNull(material, "Material can't be null!");
        return dataOf(Bukkit.createBlockData(material));
    }

    @Override
    public IBlockData dataOf(@NonNull final RWGMaterial material) {
        Objects.requireNonNull(material, "RWGMaterial can't be null!");
        return dataOf(access.asBukkit(material));
    }

    @CallerSensitive
    @Override
    public IBlockData dataOf(@NonNull final NbtCompound compound) {
        Objects.requireNonNull(compound, "NbtCompound can't be null!");
        final Optional<Class<?>> clazz = Tracker.getCallerClass();
        if (clazz.isPresent() && BlockDataParser.class.isAssignableFrom(clazz.get())) {
            logger.log(LogTypeId.WARNING,
                "BlockDataParser (" + clazz.get().getName() + ") is calling dataOf(NbtCompound), this could cause an infinite loop!");
        }
        return parserManager.parse(compound);
    }

}
