package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.util.data.JsonIO;

public class DefaultMinecraftParser extends BlockDataParser {

    public DefaultMinecraftParser(final Plugin plugin) {
        super(plugin, "minecraft");
    }

    @Override
    public IBlockData parse(final IBlockAccess access, final NbtCompound compound) {
        final String id = compound.getString("id");
        final NbtCompound states = compound.getCompound("states");
        final StringBuilder builder = new StringBuilder("[");
        for (final String key : states.getKeys()) {
            builder.append(key).append('=').append(states.getString(key)).append(',');
        }
        final String state = builder.substring(0, builder.length() == 1 ? 1 : builder.length() - 1) + ']';
        final IBlockData data = access.dataOf("minecraft:" + id + state);
        if (data == null || !compound.hasKey("properties", NbtType.COMPOUND)) {
            return data;
        }
        final JsonValue<?> value = JsonIO.fromNbt(compound.getCompound("properties"));
        if (!value.hasType(ValueType.OBJECT)) {
            return data;
        }
        data.getProperties().set(JsonIO.fromJson((JsonObject) value).asArray());
        return data;
    }

}
