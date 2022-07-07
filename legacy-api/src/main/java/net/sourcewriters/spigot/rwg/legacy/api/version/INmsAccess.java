package net.sourcewriters.spigot.rwg.legacy.api.version;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;

public interface INmsAccess {

    @NonNull
    INmsNbtAccess getNbtAccess();

    @NonNull
    INmsBiomeAccess getBiomeAccess();

    @NonNull
    INmsWorldAccess getWorldAccess();

}
