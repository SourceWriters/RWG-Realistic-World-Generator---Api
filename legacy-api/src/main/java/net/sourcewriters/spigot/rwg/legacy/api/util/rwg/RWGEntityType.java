package net.sourcewriters.spigot.rwg.legacy.api.util.rwg;

import org.bukkit.entity.EntityType;

public enum RWGEntityType {

    MUSHROOM_COW("mooshroom");

    private String minecraftName;

    RWGEntityType(final String minecraftName) {
        this.minecraftName = minecraftName;
    }

    public String minecraftName() {
        return minecraftName;
    }

    public static String toMinecraft(final EntityType type) {
        for (final RWGEntityType entity : RWGEntityType.values()) {
            if (type.name().equalsIgnoreCase(entity.name())) {
                return entity.minecraftName;
            }
        }
        return type.name().toLowerCase();
    }

}
