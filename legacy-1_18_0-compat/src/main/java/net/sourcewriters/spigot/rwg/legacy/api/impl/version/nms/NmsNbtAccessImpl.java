package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;

import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtByteArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtDouble;
import com.syntaxphoenix.syntaxapi.nbt.NbtEnd;
import com.syntaxphoenix.syntaxapi.nbt.NbtFloat;
import com.syntaxphoenix.syntaxapi.nbt.NbtInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtIntArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtLong;
import com.syntaxphoenix.syntaxapi.nbt.NbtLongArray;
import com.syntaxphoenix.syntaxapi.nbt.NbtShort;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;

public final class NmsNbtAccessImpl implements INmsNbtAccess {

    @Override
    public NbtCompound itemToCompound(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        final CompoundTag tag = new CompoundTag();
        stack.save(tag);
        return (NbtCompound) convert(tag);
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(final NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(ItemStack.of((CompoundTag) convert(compound)));
    }

    @Override
    public NbtTag fromMinecraftTag(final Object nmsTag) {
        if (nmsTag == null || !(nmsTag instanceof Tag)) {
            return null;
        }
        return convert((Tag) nmsTag);
    }

    public NbtTag convert(final Tag tag) {
        switch (NbtType.getById(tag.getId())) {
        case END:
            return NbtEnd.INSTANCE;
        case BYTE:
            return new NbtByte(((ByteTag) tag).getAsByte());
        case BYTE_ARRAY:
            return new NbtByteArray(((ByteArrayTag) tag).getAsByteArray());
        case DOUBLE:
            return new NbtDouble(((DoubleTag) tag).getAsDouble());
        case FLOAT:
            return new NbtFloat(((FloatTag) tag).getAsFloat());
        case INT:
            return new NbtInt(((IntTag) tag).getAsInt());
        case INT_ARRAY:
            return new NbtIntArray(((IntArrayTag) tag).getAsIntArray());
        case LONG:
            return new NbtLong(((LongTag) tag).getAsLong());
        case LONG_ARRAY:
            return new NbtLongArray(((LongArrayTag) tag).getAsLongArray());
        case SHORT:
            return new NbtShort(((ShortTag) tag).getAsShort());
        case STRING:
            return new NbtString(((StringTag) tag).getAsString());
        case LIST:
            final ListTag listTag = (ListTag) tag;
            final NbtList<?> targetList = new NbtList<>(NbtType.getById(listTag.getElementType()));
            for (final Tag listEntry : listTag) {
                targetList.addTag(convert(listEntry));
            }
            return targetList;
        case COMPOUND:
            final CompoundTag compoundTag = (CompoundTag) tag;
            final NbtCompound targetCompound = new NbtCompound();
            for (final String key : compoundTag.getAllKeys()) {
                targetCompound.set(key, convert(compoundTag.get(key)));
            }
            return targetCompound;
        default:
            return null;
        }
    }

    @Override
    public Object toMinecraftTag(final NbtTag tag) {
        return convert(tag);
    }

    public Tag convert(final NbtTag tag) {
        switch (tag.getType()) {
        case END:
            return EndTag.INSTANCE;
        case BYTE:
            return ByteTag.valueOf(((NbtByte) tag).getValue());
        case BYTE_ARRAY:
            return new ByteArrayTag(((NbtByteArray) tag).getValue());
        case DOUBLE:
            return DoubleTag.valueOf(((NbtDouble) tag).getValue());
        case FLOAT:
            return FloatTag.valueOf(((NbtFloat) tag).getValue());
        case INT:
            return IntTag.valueOf(((NbtInt) tag).getValue());
        case INT_ARRAY:
            return new IntArrayTag(((NbtIntArray) tag).getValue());
        case LONG:
            return LongTag.valueOf(((NbtLong) tag).getValue());
        case LONG_ARRAY:
            return new LongArrayTag(((NbtLongArray) tag).getValue());
        case SHORT:
            return ShortTag.valueOf(((NbtShort) tag).getValue());
        case STRING:
            return StringTag.valueOf(((NbtString) tag).getValue());
        case LIST:
            final NbtList<?> listTag = (NbtList<?>) tag;
            final ListTag targetList = new ListTag();
            final int size = listTag.size();
            for (int index = 0; index < size; index++) {
                targetList.addTag(index, convert(listTag.get(index)));
            }
            return targetList;
        case COMPOUND:
            final NbtCompound compoundTag = (NbtCompound) tag;
            final CompoundTag targetCompound = new CompoundTag();
            for (final String key : compoundTag.getKeys()) {
                targetCompound.put(key, convert(compoundTag.get(key)));
            }
            return targetCompound;
        default:
            return null;
        }
    }

}
