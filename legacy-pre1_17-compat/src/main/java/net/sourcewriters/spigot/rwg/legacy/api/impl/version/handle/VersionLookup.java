package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;

public abstract class VersionLookup {

    public abstract void setup(final AccessorProvider provider, final VersionProvider version);

}
