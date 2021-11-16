package net.sourcewriters.spigot.rwg.legacy.api.version;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.minecraft.ProfileCache;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGMaterial;

public interface IConversionAccess {

    @NonNull
    default ItemStack asHeadItem(@NonNull final String texture) {
        return asHeadItem(ProfileCache.asProfile(texture));
    }

    @NonNull
    ItemStack asHeadItem(@NonNull GameProfile profile);

    @NonNull
    Material asBukkit(@NonNull RWGMaterial material);

    TreeType getTreeType(@NonNull String name);

    boolean isGiantTree(TreeType type);

}
