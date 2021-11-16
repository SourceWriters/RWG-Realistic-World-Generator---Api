package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.Checks;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.HashUtil;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class VersionSpecificDataFixer extends VersionDataFixer {

    private final MinecraftVersion[] versions;

    public VersionSpecificDataFixer(final Plugin plugin, final String namespace, final MinecraftVersion... versions) {
        super(HashUtil.hash(Checks.isNotNullOrEmpty(versions, "MinecraftVersion versions")), plugin, namespace);
        this.versions = versions;
    }

    public final MinecraftVersion[] getVersions() {
        final MinecraftVersion[] array = new MinecraftVersion[versions.length];
        System.arraycopy(versions, 0, array, 0, array.length);
        return array;
    }

    @Override
    public final boolean isSupported(final MinecraftVersion version) {
        Objects.requireNonNull(version, "MinecraftVersion can't be null!");
        for (final MinecraftVersion current : versions) {
            if (current.isSimilar(version)) {
                return true;
            }
        }
        return false;
    }

}
