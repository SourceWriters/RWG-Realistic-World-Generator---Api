package net.sourcewriters.spigot.rwg.legacy.api.impl;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.VersionCompatibilityProvider;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataLoaderManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataParserManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataPlacerManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.BlockAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.data.argument.IArgumentMap;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.impl.DataFixHandlerImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.data.ForwardFixer1_14;
import net.sourcewriters.spigot.rwg.legacy.api.impl.data.ForwardFixer1_16;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.VersionAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.version.IVersionAccess;

public class VersionCompatibilityProviderImpl extends VersionCompatibilityProvider {

    @Override
    public void provide(ILogger logger, IArgumentMap map) {
        IDataFixHandler dataFixHandler = new DataFixHandlerImpl();
        IVersionAccess versionAccess = new VersionAccessImpl(logger);
        set(map, IBlockAccess.class, new BlockAccessImpl(logger, versionAccess.getConversionAccess(), dataFixHandler));
        set(map, IVersionAccess.class, versionAccess);
        set(map, IDataFixHandler.class, dataFixHandler);
    }

    @Override
    public void setup(RealisticWorldGenerator api, Plugin plugin, ILogger logger) {
        
        IDataFixHandler dataFixHandler = api.getDataFixHandler();
        IBlockAccess blockAccess = api.getBlockAccess();

        dataFixHandler.register(new ForwardFixer1_14(plugin));
        dataFixHandler.register(new ForwardFixer1_16(plugin));

        IBlockDataLoaderManager loaderManager = blockAccess.getLoaderManager();
        loaderManager.register(new DefaultMinecraftLoader(plugin));

        IBlockDataParserManager parserManager = blockAccess.getParserManager();
        parserManager.register(new DefaultMinecraftParser(plugin));

        IBlockDataPlacerManager placerManager = blockAccess.getPlacerManager();
        placerManager.register(new DefaultMinecraftPlacer(plugin));
        
    }

}
