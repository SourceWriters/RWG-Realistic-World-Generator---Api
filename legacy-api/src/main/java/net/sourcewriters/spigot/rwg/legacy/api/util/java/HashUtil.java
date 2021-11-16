package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public final class HashUtil {

    private HashUtil() {}

    private static long multiplier(final int index) {
        return index == 0 ? 1 : speedPow(32, index - 1);
    }

    private static long speedPow(long value, final int amount) {
        final long start = value;
        for (int x = 0; x < amount; x++) {
            value *= start;
        }
        return value;
    }

    public static long hash(final ServerVersion... versions) {
        long hash = 0;
        int index = 0;
        for (final ServerVersion version : versions) {
            hash += version.asSpecialHash() * multiplier(index++);
        }
        return hash;
    }

    public static long hash(final MinecraftVersion... versions) {
        long hash = 0;
        int index = 0;
        for (final MinecraftVersion version : versions) {
            hash += version.asSpecialHash() * multiplier(index++);
        }
        return hash;
    }

}
