package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsBiomeAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsNbtAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms.NmsWorldAccessImpl;
import net.sourcewriters.spigot.rwg.legacy.api.version.INmsAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;

public final class NmsAccessImpl implements INmsAccess {

    private final NmsNbtAccessImpl nbtAccess;
    private final NmsBiomeAccessImpl biomeAccess;
    private final NmsWorldAccessImpl worldAccess;

    public NmsAccessImpl(final ILogger logger) {
        nbtAccess = new NmsNbtAccessImpl();
        biomeAccess = new NmsBiomeAccessImpl();
        worldAccess = new NmsWorldAccessImpl();
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
