package net.sourcewriters.spigot.rwg.legacy.api.version.handle;

import static net.sourcewriters.spigot.rwg.legacy.api.version.handle.FakeLookup.FAKE;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public class ClassLookupProvider {

    public static final String CB_PATH_FORMAT = "org.bukkit.craftbukkit.%s.%s";
    public static final String NMS_PATH_FORMAT = "net.minecraft.server.%s.%s";

    public static final ClassLookupProvider DEFAULT = new ClassLookupProvider();

    protected final ClassLookupCache cache;

    protected final String cbPath;
    protected final String nmsPath;

    protected final ServerVersion version;

    private boolean skip = false;

    public ClassLookupProvider() {
        this((Consumer<ClassLookupProvider>) null);
    }

    public ClassLookupProvider(Consumer<ClassLookupProvider> setup) {
        this(new ClassLookupCache(), setup);
    }

    public ClassLookupProvider(ClassLookupCache cache) {
        this(cache, null);
    }

    public ClassLookupProvider(ClassLookupCache cache, Consumer<ClassLookupProvider> setup) {
        this.cache = cache;
        this.version = Versions.getServer();
        this.cbPath = String.format(CB_PATH_FORMAT, Versions.getServerAsString(), "%s");
        this.nmsPath = String.format(NMS_PATH_FORMAT, Versions.getServerAsString(), "%s");
        if (setup != null) {
            setup.accept(this);
        }
    }

    @NonNull
    public ServerVersion getVersion() {
        return version;
    }

    /*
     * Delete
     */

    public void deleteByName(String name) {
        cache.delete(name);
    }

    public void deleteByPackage(String path) {
        Entry<String, ClassLookup>[] array = cache.entries();
        for (Entry<String, ClassLookup> entry : array) {
            if (!entry.getValue().getOwner().getPackageName().equals(path)) {
                continue;
            }
            cache.delete(entry.getKey());
        }
    }

    /*
     * Skip
     */

    public ClassLookupProvider require(boolean skip) {
        this.skip = !skip;
        return this;
    }

    public ClassLookupProvider skip(boolean skip) {
        this.skip = skip;
        return this;
    }

    public boolean skip() {
        return skip;
    }

    /*
     * Reflection
     */

    @NonNull
    public ClassLookupCache getCache() {
        return cache;
    }

    @NonNull
    public String getNmsPath() {
        return nmsPath;
    }

    @NonNull
    public String getCbPath() {
        return cbPath;
    }

    @NonNull
    public ClassLookup createNMSLookup(String name, String path) {
        return skip ? FAKE : cache.create(name, getNMSClass(path));
    }

    @NonNull
    public ClassLookup createCBLookup(String name, String path) {
        return skip ? FAKE : cache.create(name, getCBClass(path));
    }

    @NonNull
    public ClassLookup createLookup(String name, String path) {
        return skip ? FAKE : cache.create(name, getClass(path));
    }

    @NonNull
    public ClassLookup createLookup(String name, Class<?> clazz) {
        return skip ? FAKE : cache.create(name, clazz);
    }

    @NonNull
    public Optional<ClassLookup> getOptionalLookup(String name) {
        return cache.get(name);
    }

    public ClassLookup getLookup(String name) {
        return cache.get(name).orElse(null);
    }

    public Class<?> getNMSClass(String path) {
        return getClass(String.format(nmsPath, path));
    }

    public Class<?> getCBClass(String path) {
        return getClass(String.format(cbPath, path));
    }

    public Class<?> getClass(String path) {
        return ClassCache.getClass(path);
    }

}