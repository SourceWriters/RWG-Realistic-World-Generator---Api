package net.sourcewriters.spigot.rwg.legacy.api.util.java;

import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;

public final class HashUtil {

    private HashUtil() {}

    private static long multiplier(int index) {
        return index == 0 ? 1 : speedPow(32, index - 1);
    }

    private static long speedPow(long value, int amount) {
        long start = value;
        for (int x = 0; x < amount; x++) {
            value *= start;
        }
        return value;
    }

    public static long hash(ServerVersion... versions) {
        long hash = 0;
        int index = 0;
        for (ServerVersion version : versions) {
            hash += version.asSpecialHash() * multiplier(index++);
        }
        return hash;
    }

    public static long hash(MinecraftVersion... versions) {
        long hash = 0;
        int index = 0;
        for (MinecraftVersion version : versions) {
            hash += version.asSpecialHash() * multiplier(index++);
        }
        return hash;
    }

}
