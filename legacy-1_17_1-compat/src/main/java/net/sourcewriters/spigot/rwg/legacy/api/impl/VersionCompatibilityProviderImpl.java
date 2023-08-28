package net.sourcewriters.spigot.rwg.legacy.api.impl;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.VersionCompatibilityProvider;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataLoaderManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataParserManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataPlacerManager;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftLoader;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.impl.DefaultMinecraftPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.data.argument.IArgumentMap;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.impl.DataFixHandlerImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.trade.TradeListingManagerImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.VersionAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.trade.ITradeListingManager;
import net.sourcewriters.spigot.rwg.legacy.api.version.IVersionAccess;

public class VersionCompatibilityProviderImpl extends VersionCompatibilityProvider {

    @Override
    public void provide(final ILogger logger, final IArgumentMap map) {
        set(map, IDataFixHandler.class, new DataFixHandlerImpl());
        set(map, IVersionAccess.class, new VersionAccessImpl(logger));
        set(map, ITradeListingManager.class, new TradeListingManagerImpl(logger));
    }

    @Override
    public void setup(final RealisticWorldGenerator api, final Plugin plugin, final ILogger logger) {

        final IBlockAccess blockAccess = api.getBlockAccess();

        final IBlockDataLoaderManager loaderManager = blockAccess.getLoaderManager();
        loaderManager.register(new DefaultMinecraftLoader(plugin));

        final IBlockDataParserManager parserManager = blockAccess.getParserManager();
        parserManager.register(new DefaultMinecraftParser(plugin));

        final IBlockDataPlacerManager placerManager = blockAccess.getPlacerManager();
        placerManager.register(new DefaultMinecraftPlacer(plugin));
        
    }
    
    @Override
    public void shutdown(RealisticWorldGenerator api, ILogger logger) {
        ((TradeListingManagerImpl) api.getTradeListingManager()).uninject();
    }

}
