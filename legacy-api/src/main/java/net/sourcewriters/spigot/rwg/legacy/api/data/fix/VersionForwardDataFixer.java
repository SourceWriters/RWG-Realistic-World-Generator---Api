package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class VersionForwardDataFixer extends VersionDataFixer {

    private final MinecraftVersion version;

    public VersionForwardDataFixer(final Plugin plugin, final String namespace, final MinecraftVersion version) {
        super(Objects.requireNonNull(version, "MinecraftVersion can't be null!").asSpecialHash(), plugin, namespace);
        this.version = version;
    }

    public final MinecraftVersion getVersion() {
        return version;
    }

    @Override
    public final boolean isSupported(final MinecraftVersion version) {
        return Objects.requireNonNull(version, "MinecraftVersion can't be null!").compareTo(this.version) >= 0;
    }

}
