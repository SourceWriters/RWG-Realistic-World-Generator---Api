package net.sourcewriters.spigot.rwg.legacy.api.util;

import java.util.Objects;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import com.google.common.base.Preconditions;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.sourcewriters.spigot.rwg.legacy.api.generator.forward.ForwardHelper;

public final class Checks {

    private Checks() {}

    public static boolean isRwg(final World world) {
        return isRwg(Objects.requireNonNull(world, "World can't be null!").getGenerator());
    }

    public static boolean isRwg(final ChunkGenerator generator) {
        return ForwardHelper.isForward(generator);
    }

    public static <T> T[] isNotNullOrEmpty(final T[] array, final String name) {
        Objects.requireNonNull(array, name + " can't be null!");
        Preconditions.checkArgument(array.length != 0, name + " can't be empty!");
        return array;
    }

    public static NbtCompound hasKey(final NbtCompound compound, final String key) {
        Preconditions.checkArgument(compound.hasKey(key), "NbtCompound has to contain key '" + key + "'!");
        return compound;
    }

    public static NbtCompound hasKey(final NbtCompound compound, final String key, final NbtType type) {
        Preconditions.checkArgument(compound.hasKey(key, type),
            "NbtCompound has to contain key '" + key + "' with type '" + type.name() + "'!");
        return compound;
    }

    public static <E extends NbtTag> NbtList<E> hasType(final NbtList<E> list, final NbtType type) {
        Preconditions.checkArgument(list.getElementType() != type, "NbtList has to contain type '" + type.name() + "'");
        return list;
    }

}
