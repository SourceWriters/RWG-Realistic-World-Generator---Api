package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public final class InstanceBuilder {

    private InstanceBuilder() {}

    public static <A> Optional<A> buildBelow(Class<A> abstraction) {
        ServerVersion server = Versions.getServer();
        int major = server.getMajor();
        Optional<Class<?>> optional = Optional.empty();
        String base = abstraction.getPackageName() + "." + abstraction.getSimpleName();
        for (int minor = server.getMinor(); minor >= 13; minor--) {
            optional = ClassCache.getOptionalClass(base + major + '_' + minor).filter(abstraction::isAssignableFrom);
            if (optional.isPresent()) {
                break;
            }
        }
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        ClassLookup lookup = ClassLookup.of(optional.get());
        Object object = lookup.init();
        if (object == null) {
            lookup.delete();
            return Optional.empty();
        }
        lookup.delete();
        return Optional.of(abstraction.cast(object));
    }

    public static <A> Optional<A> buildExact(Class<A> abstraction) {
        ServerVersion server = Versions.getServer();
        int major = server.getMajor();
        int minor = server.getMinor();
        Optional<Class<?>> optional = ClassCache
            .getOptionalClass(abstraction.getPackageName() + "." + abstraction.getSimpleName() + major + '_' + minor)
            .filter(abstraction::isAssignableFrom);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        ClassLookup lookup = ClassLookup.of(optional.get());
        Object object = lookup.init();
        if (object == null) {
            lookup.delete();
            return Optional.empty();
        }
        lookup.delete();
        return Optional.of(abstraction.cast(object));
    }

}
