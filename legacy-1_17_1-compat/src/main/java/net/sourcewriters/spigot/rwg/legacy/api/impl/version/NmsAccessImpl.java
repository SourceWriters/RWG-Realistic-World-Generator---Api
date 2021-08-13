package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle.GlobalLookup;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsBiomeAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsNbtAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsWorldAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.version.INmsAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;

public final class NmsAccessImpl implements INmsAccess {

    private final ClassLookupProvider provider = new ClassLookupProvider(GlobalLookup::setup);

    private final NmsNbtAccessImpl nbtAccess;
    private final NmsBiomeAccessImpl biomeAccess;
    private final NmsWorldAccessImpl worldAccess;

    public NmsAccessImpl(ILogger logger) {
        nbtAccess = new NmsNbtAccessImpl();
        biomeAccess = new NmsBiomeAccessImpl(provider);
        worldAccess = new NmsWorldAccessImpl(provider);
    }

    @Override
    public final ClassLookupProvider getLookupProvider() {
        return provider;
    }

    @Override
    public final INmsNbtAccess getNbtAccess() {
        return nbtAccess;
    }

    @Override
    public final INmsBiomeAccess getBiomeAccess() {
        return biomeAccess;
    }

    @Override
    public final INmsWorldAccess getWorldAccess() {
        return worldAccess;
    }

}
