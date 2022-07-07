package net.sourcewriters.spigot.rwg.legacy.api.version.provider;

import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public final class MappedVersionProvider extends VersionProvider {

    public MappedVersionProvider(ServerVersion version) {
        super(version);
    }

    @Override
    protected String buildMinecraftPath(ServerVersion version) {
        return "net.minecraft.%s";
    }

}
