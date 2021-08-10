package net.sourcewriters.spigot.rwg.legacy.api.impl.version;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGMaterial;
import net.sourcewriters.spigot.rwg.legacy.api.version.IConversionAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class ConversionAccessImpl implements IConversionAccess {

    private final ClassLookupProvider provider;

    public ConversionAccessImpl(ClassLookupProvider provider) {
        this.provider = provider;
    }

    @Override
    public final Material asBukkit(RWGMaterial material) {
        switch (material) {
        case HEAD:
            return Material.getMaterial("PLAYER_HEAD");
        case HEAD_WALL:
            return Material.getMaterial("PLAYER_WALL_HEAD");
        case HEAD_ITEM:
            return Material.getMaterial("PLAYER_HEAD");
        case BLACK_GLASS_PANE:
            return Material.getMaterial("BLACK_STAINED_GLASS_PANE");
        case SPAWNER:
            return Material.getMaterial("SPAWNER");
        default:
            return Material.AIR;
        }
    }

    @Override
    public final TreeType getTreeType(String name) {
        try {
            return TreeType.valueOf(name);
        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }

    @Override
    public final boolean isGiantTree(TreeType type) {
        switch (type) {
        case JUNGLE:
        case MEGA_REDWOOD:
        case DARK_OAK:
            return true;
        default:
            return false;
        }
    }

    @Override
    public final ItemStack asHeadItem(GameProfile profile) {
        ItemStack stack = new ItemStack(RWGMaterial.HEAD_ITEM.asBukkit(this));
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        provider.getLookup("bkt_skull_meta").setFieldValue(meta, "profile", profile);
        stack.setItemMeta(meta);
        return stack;
    }

}
