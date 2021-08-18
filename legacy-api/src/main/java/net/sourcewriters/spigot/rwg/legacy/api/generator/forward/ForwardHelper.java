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

    public static boolean isForward(ChunkGenerator generator) {
        if (generator == null) {
            return false;
        }
        Class<?> clazz = generator.getClass();
        String name = clazz.getName();
        if (LIST.contains(name)) {
            return CACHE.get(name).isPresent();
        }
        ClassLookup lookup = CACHE.create(name, clazz);
        Object object = lookup.searchMethod("id", "getIdentifier").run(generator, "id");
        boolean valid = (object != null && ((long) object) == 345679324062398605L);
        LIST.add(name);
        if (!valid) {
            CACHE.delete(name);
            return false;
        }
        lookup.searchMethod("set", "setGenerator", ChunkGenerator.class).searchMethod("get", "getGenerator").searchMethod("populators",
            "setPopulators", BlockPopulator[].class);
        return valid;
    }

    public static IRwgGenerator get(World world) {
        return world != null ? get(world.getGenerator()) : null;
    }

    public static IRwgGenerator get(ChunkGenerator generator) {
        if (!isForward(generator)) {
            return null;
        }
        Object found = CACHE.get(generator.getClass().getName()).orElse(null).run(generator, "get");
        return found instanceof IRwgGenerator ? (IRwgGenerator) found : null;
    }

    public static boolean set(World world, Function<World, ChunkGenerator> builder) {
        if (world == null || world.getGenerator() == null) {
            return false;
        }
        ChunkGenerator current = world.getGenerator();
        if (!isForward(current)) {
            return false;
        }
        ChunkGenerator generator = builder.apply(world);
        ClassLookup lookup = CACHE.get(current.getClass().getName()).orElse(null);
        lookup.run(current, "set", generator);
        List<BlockPopulator> list = generator.getDefaultPopulators(world);
        lookup.run(current, "populators", (Object[]) new Object[] {
            list == null ? new BlockPopulator[0] : list.stream().filter(obj -> obj != null).toArray(BlockPopulator[]::new)
        });
        return true;
    }

    public static void clear() {
        LIST.clear();
    }

}
