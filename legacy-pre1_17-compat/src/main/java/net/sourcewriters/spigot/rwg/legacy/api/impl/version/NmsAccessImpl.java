package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle.GlobalLookup;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsBiomeAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsNbtAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsWorldAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.INmsAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;

public final class NmsAccessImpl implements INmsAccess {

    private final AccessorProvider provider = new AccessorProvider();

    private final NmsNbtAccessImpl nbtAccess;
    private final NmsBiomeAccessImpl biomeAccess;
    private final NmsWorldAccessImpl worldAccess;

    public NmsAccessImpl(final ILogger logger) {
        GlobalLookup.setup(provider, VersionProvider.get());
        nbtAccess = new NmsNbtAccessImpl(provider, logger);
        biomeAccess = new NmsBiomeAccessImpl(provider);
        worldAccess = new NmsWorldAccessImpl(provider, nbtAccess);
    }

    public AccessorProvider getProvider() {
        return provider;
    }

    @Override
    public INmsNbtAccess getNbtAccess() {
        return nbtAccess;
    }

    @Override
    public INmsBiomeAccess getBiomeAccess() {
        return biomeAccess;
    }

    @Override
    public INmsWorldAccess getWorldAccess() {
        return worldAccess;
    }

}
