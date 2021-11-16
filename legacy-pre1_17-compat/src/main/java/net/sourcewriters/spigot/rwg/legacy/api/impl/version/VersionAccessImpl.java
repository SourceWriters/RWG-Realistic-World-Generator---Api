package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.version.IBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.IConversionAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.INmsAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.IVersionAccess;

public final class VersionAccessImpl implements IVersionAccess {

    private final NmsAccessImpl nmsAccess;
    private final ConversionAccessImpl conversionAccess;
    private final BiomeAccessImpl biomeAccess;

    public VersionAccessImpl(final ILogger logger) {
        nmsAccess = new NmsAccessImpl(logger);
        conversionAccess = new ConversionAccessImpl(nmsAccess.getLookupProvider());
        biomeAccess = new BiomeAccessImpl();
    }

    @Override
    public INmsAccess getNmsAccess() {
        return nmsAccess;
    }

    @Override
    public IConversionAccess getConversionAccess() {
        return conversionAccess;
    }

    @Override
    public IBiomeAccess getBiomeAccess() {
        return biomeAccess;
    }

}
