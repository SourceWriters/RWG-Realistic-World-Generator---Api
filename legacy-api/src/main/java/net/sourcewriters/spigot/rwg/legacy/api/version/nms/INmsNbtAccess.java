package net.sourcewriters.spigot.rwg.legacy.api.version.nms;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface INmsNbtAccess {

    @NonNull
    NbtCompound itemToCompound(@NonNull ItemStack itemStack);

    @NonNull
    ItemStack itemFromCompound(@NonNull NbtCompound compound);

    @NonNull
    NbtTag fromMinecraftTag(@NonNull Object nmsTag);

    @NonNull
    Object toMinecraftTag(@NonNull NbtTag tag);

}
