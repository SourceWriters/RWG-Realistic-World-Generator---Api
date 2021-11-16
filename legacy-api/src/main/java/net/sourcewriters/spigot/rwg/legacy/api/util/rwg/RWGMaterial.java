package net.sourcewriters.spigot.rwg.legacy.api.util.rwg;

import java.util.Objects;

import org.bukkit.Material;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.IConversionAccess;

public enum RWGMaterial {

    HEAD,
    HEAD_WALL,
    HEAD_ITEM,
    BLACK_GLASS_PANE,
    SPAWNER;

    @NonNull
    public Material asBukkit(@NonNull final IConversionAccess access) {
        return Objects.requireNonNull(access, "IConversionAccess can't be null!").asBukkit(this);
    }

    public static Material getBukkitMaterial(@NonNull final IConversionAccess access, @NonNull final String material) {
        Objects.requireNonNull(material, "String material can't be null!");
        try {
            return Material.valueOf(material.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException ignore0) {
            try {
                return RWGMaterial.valueOf(material.toUpperCase()).asBukkit(access);
            } catch (NullPointerException | IllegalArgumentException ignore1) {
                return null;
            }
        }
    }

}
