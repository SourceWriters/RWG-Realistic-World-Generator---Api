package net.sourcewriters.spigot.rwg.legacy.api.version;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IVersionAccess {

    @NonNull
    INmsAccess getNmsAccess();

    @NonNull
    IConversionAccess getConversionAccess();

    @NonNull
    IBiomeAccess getBiomeAccess();

}
