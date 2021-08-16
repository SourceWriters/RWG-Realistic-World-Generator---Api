package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import java.lang.reflect.Constructor;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public final class InstanceBuilder {

    private InstanceBuilder() {}

    public static <A> Optional<A> buildBelow(Class<A> abstraction) {
        ServerVersion server = Versions.getServer();
        int major = server.getMajor();
        Optional<Class<?>> optional = Optional.empty();
        String base = abstraction.getPackageName() + "." + abstraction.getSimpleName() + major + "_";
        for (int minor = server.getMinor(); minor >= 13; minor--) {
            optional = loadClass(abstraction, base + minor).filter(abstraction::isAssignableFrom);
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
        Optional<Class<?>> optional = loadClass(abstraction, abstraction.getPackageName() + "." + abstraction.getSimpleName() + major + "_" + minor)
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

    private static Optional<Class<?>> loadClass(Class<?> search, String name) {
        try {
            return Optional.ofNullable(Class.forName(name, true, search.getClassLoader()));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static <T> T create(Class<T> clazz, Object... arguments) throws Exception {
        Constructor<?>[] constructors = Arrays.merge(Constructor[]::new, clazz.getConstructors(), clazz.getDeclaredConstructors());
        Class<?>[] classes = new Class<?>[arguments.length];
        for (int index = 0; index < arguments.length; index++) {
            classes[index] = arguments[index].getClass();
        }
        int max = classes.length;
        Constructor<?> builder = null;
        int args = 0;
        int[] argIdx = new int[max];
        for (Constructor<?> constructor : constructors) {
            int count = constructor.getParameterCount();
            if (count > max || count < args) {
                continue;
            }
            int[] tmpIdx = new int[max];
            for (int idx = 0; idx < max; idx++) {
                tmpIdx[idx] = -1;
            }
            Class<?>[] types = constructor.getParameterTypes();
            int tmpArgs = 0;
            for (int index = 0; index < count; index++) {
                for (int idx = 0; idx < max; idx++) {
                    if (!types[index].equals(classes[idx])) {
                        continue;
                    }
                    tmpIdx[idx] = index;
                    tmpArgs++;
                }
            }
            if (tmpArgs != count) {
                continue;
            }
            argIdx = tmpIdx;
            args = tmpArgs;
            builder = constructor;
        }
        if (builder == null) {
            return null;
        }
        if (args == 0) {
            return clazz.cast(builder.newInstance());
        }
        Object[] parameters = new Object[args];
        for (int idx = 0; idx < max; idx++) {
            if (argIdx[idx] == -1) {
                continue;
            }
            parameters[argIdx[idx]] = arguments[idx];
        }
        return clazz.cast(builder.newInstance(parameters));
    }

}
