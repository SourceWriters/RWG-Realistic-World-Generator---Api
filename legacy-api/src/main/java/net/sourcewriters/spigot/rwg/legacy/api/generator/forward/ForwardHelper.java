package net.sourcewriters.spigot.rwg.legacy.api.generator.forward;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.generator.IRwgGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupCache;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class ForwardHelper {

    private static final ClassLookupCache CACHE = ClassLookupProvider.DEFAULT.getCache();
    private static final ArrayList<String> LIST = new ArrayList<>();

    private ForwardHelper() {}

    public static boolean isForward(final ChunkGenerator generator) {
        if (generator == null) {
            return false;
        }
        final Class<?> clazz = generator.getClass();
        final String name = clazz.getName();
        if (LIST.contains(name)) {
            return CACHE.get(name).isPresent();
        }
        final ClassLookup lookup = CACHE.create(name, clazz);
        final Object object = lookup.searchMethod("id", "getIdentifier").run(generator, "id");
        final boolean valid = object != null && (long) object == 345679324062398605L;
        LIST.add(name);
        if (!valid) {
            CACHE.delete(name);
            return false;
        }
        lookup.searchMethod("set", "setGenerator", ChunkGenerator.class).searchMethod("get", "getGenerator").searchMethod("populators",
            "setPopulators", BlockPopulator[].class);
        return valid;
    }

    public static IRwgGenerator get(final World world) {
        return world != null ? get(world.getGenerator()) : null;
    }

    public static IRwgGenerator get(final ChunkGenerator generator) {
        if (!isForward(generator)) {
            return null;
        }
        final Object found = CACHE.get(generator.getClass().getName()).orElse(null).run(generator, "get");
        return found instanceof IRwgGenerator ? (IRwgGenerator) found : null;
    }

    public static boolean set(final World world, final Function<World, ChunkGenerator> builder) {
        if (world == null || world.getGenerator() == null) {
            return false;
        }
        final ChunkGenerator current = world.getGenerator();
        if (!isForward(current)) {
            return false;
        }
        final ChunkGenerator generator = builder.apply(world);
        final ClassLookup lookup = CACHE.get(current.getClass().getName()).orElse(null);
        lookup.run(current, "set", generator);
        final List<BlockPopulator> list = generator.getDefaultPopulators(world);
        lookup.run(current, "populators",
            list == null ? new BlockPopulator[0] : list.stream().filter(obj -> obj != null).toArray(BlockPopulator[]::new));
        return true;
    }

    public static void clear() {
        LIST.clear();
    }

}
