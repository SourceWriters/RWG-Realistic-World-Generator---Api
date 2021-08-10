package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class VersionBackwardDataFixer extends VersionDataFixer {

    private final MinecraftVersion version;

    public VersionBackwardDataFixer(@NonNull Plugin plugin, @NonNull String namespace, @NonNull MinecraftVersion version) {
        super(Objects.requireNonNull(version, "MinecraftVersion can't be null!").asSpecialHash() * -1, plugin, namespace);
        this.version = version;
    }

    @NonNull
    public final MinecraftVersion getVersion() {
        return version;
    }

    @Override
    public final boolean isSupported(@NonNull MinecraftVersion version) {
        return Objects.requireNonNull(version, "MinecraftVersion can't be null!").compareTo(this.version) <= 0;
    }

}
