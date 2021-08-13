package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

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

import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;

public final class NmsNbtAccessImpl implements INmsNbtAccess {

    @Override
    public NbtCompound itemToCompound(org.bukkit.inventory.ItemStack itemStack) {
        return (NbtCompound) convert(CraftItemStack.asNMSCopy(itemStack).getOrCreateTag());
    }

    @Override
    public org.bukkit.inventory.ItemStack itemFromCompound(NbtCompound compound) {
        return CraftItemStack.asBukkitCopy(ItemStack.of((CompoundTag) convert(compound)));
    }

    @Override
    public NbtTag fromMinecraftTag(Object nmsTag) {
        if (nmsTag == null || !(nmsTag instanceof Tag)) {
            return null;
        }
        return convert((Tag) nmsTag);
    }

    public NbtTag convert(Tag tag) {
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
            ListTag listTag = (ListTag) tag;
            NbtList<?> targetList = new NbtList<>(NbtType.getById(listTag.getElementType()));
            for (Tag listEntry : listTag) {
                targetList.addTag(convert(listEntry));
            }
            return targetList;
        case COMPOUND:
            CompoundTag compoundTag = (CompoundTag) tag;
            NbtCompound targetCompound = new NbtCompound();
            for (String key : compoundTag.getAllKeys()) {
                targetCompound.set(key, convert(compoundTag.get(key)));
            }
            return targetCompound;
        default:
            return null;
        }
    }

    @Override
    public Object toMinecraftTag(NbtTag tag) {
        return convert(tag);
    }

    public Tag convert(NbtTag tag) {
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
            NbtList<?> listTag = (NbtList<?>) tag;
            ListTag targetList = new ListTag();
            int size = listTag.size();
            for (int index = 0; index < size; index++) {
                targetList.addTag(index, convert(listTag.get(index)));
            }
            return targetList;
        case COMPOUND:
            NbtCompound compoundTag = (NbtCompound) tag;
            CompoundTag targetCompound = new CompoundTag();
            for (String key : compoundTag.getKeys()) {
                targetCompound.put(key, convert(compoundTag.get(key)));
            }
            return targetCompound;
        default:
            return null;
        }
    }

}
