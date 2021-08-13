package net.sourcewriters.spigot.rwg.legacy.api.impl;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataLoaderManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataParserManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataPlacerManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.BlockAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.impl.DataFixHandlerImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.VersionAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.data.*;
import net.sourcewriters.spigot.rwg.legacy.api.version.IVersionAccess;

public abstract class VersionCompatibilityProviderImpl extends RealisticWorldGenerator {

    private final ILogger logger;

    private final VersionAccessImpl versionAccess;
    private final BlockAccessImpl blockAccess;

    private final DataFixHandlerImpl dataFixHandler;

    public VersionCompatibilityProviderImpl(Plugin plugin, ILogger logger) {
        super(plugin);
        this.logger = logger;
        this.dataFixHandler = new DataFixHandlerImpl();
        this.versionAccess = new VersionAccessImpl(logger);
        this.blockAccess = new BlockAccessImpl(logger, versionAccess.getConversionAccess(), dataFixHandler);
        setup(plugin, logger);
    }

    private final void setup(Plugin plugin, ILogger logger) {

        dataFixHandler.register(new BackwardFixer1_13(plugin));
        dataFixHandler.register(new BackwardFixer1_15(plugin));
        dataFixHandler.register(new ForwardFixer1_14(plugin));
        dataFixHandler.register(new ForwardFixer1_16(plugin));

        IBlockDataLoaderManager loaderManager = blockAccess.getLoaderManager();
        loaderManager.register(new DefaultMinecraftLoader(plugin));

        IBlockDataParserManager parserManager = blockAccess.getParserManager();
        parserManager.register(new DefaultMinecraftParser(plugin));

        IBlockDataPlacerManager placerManager = blockAccess.getPlacerManager();
        placerManager.register(new DefaultMinecraftPlacer(plugin));

        finalSetup(plugin, logger);
    }

    protected abstract void finalSetup(Plugin plugin, ILogger logger);

    @Override
    public final ILogger getLogger() {
        return logger;
    }

    @Override
    public final IBlockAccess getBlockAccess() {
        return blockAccess;
    }

    @Override
    public final IVersionAccess getVersionAccess() {
        return versionAccess;
    }

    @Override
    public final IDataFixHandler getDataFixHandler() {
        return dataFixHandler;
    }

}
