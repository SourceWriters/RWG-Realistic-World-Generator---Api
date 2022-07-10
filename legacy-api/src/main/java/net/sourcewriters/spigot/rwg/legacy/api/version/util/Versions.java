package net.sourcewriters.spigot.rwg.legacy.api.version.util;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.bukkit.Bukkit;

import com.syntaxphoenix.syntaxapi.version.DefaultVersion;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.function.TriPredicate;

public final class Versions {

    private static MinecraftVersion MINECRAFT_VERSION;
    private static String MINECRAFT_VERSION_STRING;

    private static ServerVersion SERVER_VERSION;
    private static String SERVER_VERSION_STRING;

    private Versions() {}

    /*
     * Minecraft
     */

    public static MinecraftVersion getMinecraft() {
        return MINECRAFT_VERSION != null ? MINECRAFT_VERSION
            : (MINECRAFT_VERSION = MinecraftVersion.fromString(Bukkit.getVersion().split(" ")[2].replace(")", "")));
    }

    public static String getMinecraftAsString() {
        return MINECRAFT_VERSION_STRING != null ? MINECRAFT_VERSION_STRING : (MINECRAFT_VERSION_STRING = getMinecraft().toString());
    }

    public static String patchMinecraft(final String input) {
        return input + getServerAsString();
    }

    /*
     * Server Version
     */

    public static ServerVersion getServer() {
        return SERVER_VERSION != null ? SERVER_VERSION
            : (SERVER_VERSION = ServerVersion.ANALYZER.analyze(Bukkit.getServer().getClass().getPackage().getName().split("\\.", 4)[3]));
    }

    public static String getServerAsString() {
        return SERVER_VERSION_STRING != null ? SERVER_VERSION_STRING : (SERVER_VERSION_STRING = getServer().toString());
    }

    public static String patchServer(final String input) {
        return input + getServerAsString();
    }

    //
    // Predicates

    public static boolean version(final int major, final int minor) {
        return major(major) && minor(minor);
    }

    public static boolean version(final int major, final int minor, final int refaction) {
        return major(major) && minor(minor) && refaction(refaction);
    }

    public static boolean major(final int... major) {
        final int server = getServer().getMajor();
        for (final int current : major) {
            if (server == current) {
                return true;
            }
        }
        return false;
    }

    public static boolean minor(final int... minor) {
        final int server = getServer().getMinor();
        for (final int current : minor) {
            if (server == current) {
                return true;
            }
        }
        return false;
    }

    public static boolean refaction(final int... refaction) {
        final int server = getServer().getRefaction();
        for (final int current : refaction) {
            if (server == current) {
                return true;
            }
        }
        return false;
    }

    public static boolean version(final TriPredicate<Integer, Integer, Integer> predicate) {
        final ServerVersion version = getServer();
        return predicate.test(version.getMajor(), version.getMinor(), version.getRefaction());
    }

    public static boolean version(final BiPredicate<Integer, Integer> predicate) {
        final ServerVersion version = getServer();
        return predicate.test(version.getMajor(), version.getMinor());
    }

    public static boolean major(final Predicate<Integer> predicate) {
        return predicate.test(getServer().getMajor());
    }

    public static boolean minor(final Predicate<Integer> predicate) {
        return predicate.test(getServer().getMinor());
    }

    public boolean refaction(final Predicate<Integer> predicate) {
        return predicate.test(getServer().getRefaction());
    }

    //
    // Compatible checks

    public static boolean isCompatible(final int major, final int minor) {
        return isCompatible(major, minor, 0);
    }

    public static boolean isCompatible(final int major, final int minor, final int patch) {
        return getMinecraft().compareTo(new DefaultVersion(major, minor, patch)) >= 0;
    }

    public static boolean isCompatibleEx(final int major, final int minor) {
        return isCompatibleEx(major, minor, 0);
    }

    public static boolean isCompatibleEx(final int major, final int minor, final int patch) {
        return getMinecraft().compareTo(new DefaultVersion(major, minor, patch)) == 0;
    }

    public static boolean isServerCompat(final int major, final int minor) {
        return isServerCompat(major, minor, 0);
    }

    public static boolean isServerCompat(final int major, final int minor, final int refaction) {
        return getServer().compareTo(new ServerVersion(major, minor, 0, refaction)) >= 0;
    }

    public static boolean isServerCompatEx(final int major, final int minor) {
        return isServerCompatEx(major, minor, 0);
    }

    public static boolean isServerCompatEx(final int major, final int minor, final int refaction) {
        return getServer().compareTo(new ServerVersion(major, minor, 0, refaction)) == 0;
    }

}