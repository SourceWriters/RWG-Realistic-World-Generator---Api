package net.sourcewriters.spigot.rwg.legacy.api.version.util;

import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.VersionAnalyzer;

public class MinecraftVersion extends DefaultVersion {

    public static final MinecraftVersion NONE = new MinecraftVersion(false);
    private static final MinecraftAnalyzer ANALYZER = new MinecraftAnalyzer();

    private final boolean valid;

    /*
     * 
     */

    private MinecraftVersion(boolean valid) {
        this.valid = valid;
    }

    public MinecraftVersion() {
        super();
        this.valid = true;
    }

    public MinecraftVersion(int major, int minor, int patch) {
        super(major, minor, patch);
        this.valid = true;
    }

    /*
     * 
     */

    public final boolean isValid() {
        return valid;
    }

    public final int asSpecialHash() {
        return Integer.hashCode(getPatch()) + (Integer.hashCode(getMinor()) * 32) + (Integer.hashCode(getMajor()) * 1024);
    }

    /*
     * 
     */

    @Override
    protected MinecraftVersion setMajor(int major) {
        return (MinecraftVersion) super.setMajor(major);
    }

    @Override
    protected MinecraftVersion setMinor(int minor) {
        return (MinecraftVersion) super.setMinor(minor);
    }

    @Override
    protected MinecraftVersion setPatch(int patch) {
        return (MinecraftVersion) super.setPatch(patch);
    }

    /*
     * 
     */

    @Override
    protected MinecraftVersion init(int major, int minor, int patch) {
        return new MinecraftVersion(major, minor, patch);
    }

    @Override
    public VersionAnalyzer getAnalyzer() {
        return ANALYZER;
    }

    /*
     * 
     */

    public static MinecraftVersion of(int major) {
        return new MinecraftVersion(major, 0, 0);
    }

    public static MinecraftVersion of(int major, int minor) {
        return new MinecraftVersion(major, minor, 0);
    }

    public static MinecraftVersion of(int major, int minor, int patch) {
        return new MinecraftVersion(major, minor, patch);
    }

    public static MinecraftVersion fromString(String versionString) {
        return ANALYZER.analyze(versionString);
    }

    public static MinecraftVersion[] fromStringArray(String... versionStrings) {
        MinecraftVersion[] versions = new MinecraftVersion[versionStrings.length];
        int index = 0;
        for (String versionString : versionStrings) {
            versions[index] = ANALYZER.analyze(versionString);
            index++;
        }
        return versions;
    }

    /*
     * 
     */

    public static class MinecraftAnalyzer implements VersionAnalyzer {
        @Override
        public MinecraftVersion analyze(String formatted) {
            if(formatted == null) {
                return MinecraftVersion.NONE;
            }
            formatted = formatted.startsWith("v") ? formatted.substring(1) : formatted;
            String[] parts;
            if (formatted.contains(".")) {
                parts = formatted.split("\\.");
            } else if (formatted.contains("_")) {
                parts = formatted.split("_");
            } else {
                return MinecraftVersion.NONE;
            }
            if (parts.length == 2 || parts.length == 3) {
                MinecraftVersion version = new MinecraftVersion();
                version.setMajor(Integer.parseInt(parts[0]));
                version.setMinor(Integer.parseInt(parts[1]));
                version.setPatch(parts.length == 3 ? Integer.parseInt(parts[2]) : 0);
                return version;
            } else {
                return MinecraftVersion.NONE;
            }
        }
    }

}
