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

    public DefaultMinecraftParser(Plugin plugin) {
        super(plugin, "minecraft");
    }

    @Override
    public IBlockData parse(IBlockAccess access, NbtCompound compound) {
        String id = compound.getString("id");
        NbtCompound states = compound.getCompound("states");
        StringBuilder builder = new StringBuilder("[");
        for (String key : states.getKeys()) {
            builder.append(key).append('=').append(states.getString(key)).append(',');
        }
        String state = builder.substring(0, builder.length() == 1 ? 1 : builder.length() - 1) + ']';
        IBlockData data = access.dataOf("minecraft:" + id + state);
        if (data == null || !compound.hasKey("properties", NbtType.COMPOUND)) {
            return data;
        }
        JsonValue<?> value = JsonIO.fromNbt(compound.getCompound("properties"));
        if (!value.hasType(ValueType.OBJECT)) {
            return data;
        }
        data.getProperties().set(JsonIO.fromJson((JsonObject) value).asArray());
        return data;
    }

}
