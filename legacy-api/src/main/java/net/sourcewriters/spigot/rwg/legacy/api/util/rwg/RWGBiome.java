package net.sourcewriters.spigot.rwg.legacy.api.util.rwg;

import java.util.Arrays;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public enum RWGBiome {

    JUNGLE,

    JUNGLE_HILLS,

    JUNGLE_MOUNTAINS,

    DESERT,

    DESERT_HILLS,

    DESERT_MOUNTAINS,

    FOREST,

    FOREST_HILLS,

    BIRCH_FOREST,

    BIRCH_FOREST_HILLS,

    EXTREME_HILLS,

    EXTREME_MOUNTAINS,

    SAVANNA,

    SAVANNA_HILLS,

    OCEAN(true),

    LUKEWARM_OCEAN(true),

    WARM_OCEAN(true),

    COLD_OCEAN(true),

    FROZEN_OCEAN(true),

    DEEP_OCEAN(true),

    DEEP_LUKEWARM_OCEAN(true),

    DEEP_WARM_OCEAN(true),

    DEEP_COLD_OCEAN(true),

    DEEP_FROZEN_OCEAN(true),

    PLAINS,

    DEATH_LAND,

    BEACH(true),

    COLD_BEACH(true),

    COLD_TAIGA,

    COLD_TAIGA_HILLS,

    COLD_TAIGA_MOUNTAINS,

    ICE_PLAINS,

    TAIGA,

    TAIGA_HILLS,

    TAIGA_MOUNTAINS,

    SWAMPLAND,

    SWAMPLAND_HILLS,

    DARK_OAK_FOREST,

    DARK_OAK_FOREST_HILLS,

    BAMBOO_JUNGLE,

    BAMBOO_JUNGLE_HILLS;

    private static final RWGBiome[] OCEANS;

    static {
        OCEANS = Arrays.stream(RWGBiome.values()).filter(biome -> biome.ocean).toArray(RWGBiome[]::new);
    }

    private final String[] cache = new String[3];
    private boolean ocean = false;

    RWGBiome() {
        cache[0] = name().toLowerCase().replace('_', '-');
        cache[1] = generateName();
        cache[2] = name();
    }

    RWGBiome(final boolean ocean) {
        this();
        this.ocean = ocean;
    }

    public String configName() {
        return cache[0];
    }

    public String biomeName() {
        return cache[1];
    }

    private String generateName() {
        final String[] parts = name().split("_");
        for (int index = 0; index < parts.length; index++) {
            parts[index] = Strings.firstLetterToUpperCase(parts[index].toLowerCase());
        }
        return Strings.toString(parts, " ");
    }

    public static RWGBiome fromString(final String str) {
        for (final RWGBiome biome : values()) {
            for (final String current : biome.cache) {
                if (current.equalsIgnoreCase(str)) {
                    return biome;
                }
            }
        }
        return null;
    }

    public static RWGBiome[] oceans() {
        final RWGBiome[] array = new RWGBiome[OCEANS.length];
        System.arraycopy(OCEANS, 0, array, 0, OCEANS.length);
        return array;
    }

}
