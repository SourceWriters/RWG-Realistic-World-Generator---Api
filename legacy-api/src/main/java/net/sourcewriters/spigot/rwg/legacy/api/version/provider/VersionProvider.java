package net.sourcewriters.spigot.rwg.legacy.api.version.provider;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.Accessor;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.JavaAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public abstract class VersionProvider {

    private static final Container<VersionProvider> PROVIDER = Container.of();

    public static VersionProvider get() {
        if (PROVIDER.isPresent()) {
            return PROVIDER.get();
        }
        return PROVIDER.replace(build()).lock().get();
    }

    private static VersionProvider build() {
        ServerVersion version = Versions.getServer();
        if (Versions.isServerCompat(1, 17)) {
            return new MappedVersionProvider(version);
        }
        return new LegacyVersionProvider(version);
    }

    private final String minecraftPath;
    private final String craftBukkitPath;
    private final String bukkitPath;

    public VersionProvider(ServerVersion version) {
        this.minecraftPath = buildMinecraftPath(version);
        this.craftBukkitPath = buildCraftBukkitPath(version);
        this.bukkitPath = buildBukkitPath(version);
    }

    protected String buildMinecraftPath(ServerVersion version) {
        return String.format("net.minecraft.server.%s.%s", version.toString(), "%s");
    }

    protected String buildCraftBukkitPath(ServerVersion version) {
        return String.format("org.bukkit.craftbukkit.%s.%s", version.toString(), "%s");
    }

    protected String buildBukkitPath(ServerVersion version) {
        return "org.bukkit.%s";
    }

    public final String minecraft(String path) {
        return String.format(minecraftPath, path);
    }

    public final String craftBukkit(String path) {
        return String.format(craftBukkitPath, path);
    }

    public final String bukkit(String path) {
        return String.format(bukkitPath, path);
    }

    public final Class<?> minecraftClass(String path) {
        return JavaAccess.findClass(minecraft(path));
    }

    public final Class<?> craftBukkitClass(String path) {
        return JavaAccess.findClass(craftBukkit(path));
    }

    public final Class<?> bukkitClass(String path) {
        return JavaAccess.findClass(bukkit(path));
    }

    public final Accessor minecraftAccess(String path) {
        return Accessor.ofNullable(minecraftClass(path));
    }

    public final Accessor craftBukkitAccess(String path) {
        return Accessor.ofNullable(craftBukkitClass(path));
    }

    public final Accessor bukkitAccess(String path) {
        return Accessor.ofNullable(bukkitClass(path));
    }

}
