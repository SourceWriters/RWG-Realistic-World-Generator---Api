package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class VersionDataFixer extends DataFixer {

    public VersionDataFixer(long versionHash, @NonNull Plugin plugin, @NonNull String namespace) {
        super(versionHash, plugin, namespace);
    }

    public abstract boolean isSupported(@NonNull MinecraftVersion version);

}
