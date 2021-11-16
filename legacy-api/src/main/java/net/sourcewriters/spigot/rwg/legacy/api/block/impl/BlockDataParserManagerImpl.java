package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataParser;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockDataParserManager;
import net.sourcewriters.spigot.rwg.legacy.api.util.Checks;
import net.sourcewriters.spigot.rwg.legacy.api.util.data.JsonIO;

public class BlockDataParserManagerImpl implements IBlockDataParserManager {

    private final ConcurrentHashMap<Long, BlockDataParser> parsers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, List<Long>> order = new ConcurrentHashMap<>();

    private final ILogger logger;
    private final IBlockAccess access;

    public BlockDataParserManagerImpl(final ILogger logger, final IBlockAccess access) {
        this.logger = logger;
        this.access = access;
    }

    @Override
    public boolean register(final BlockDataParser parser) {
        Objects.requireNonNull(parser, "BlockDataParser can't be null!");
        if (has(parser.getId())) {
            return false;
        }
        parsers.put(parser.getId(), parser);
        order.computeIfAbsent(parser.getNamespace(), ignore -> new ArrayList<>()).add(parser.getId());
        return true;
    }

    @Override
    public boolean unregister(final long id) {
        final BlockDataParser parser = parsers.remove(id);
        if (parser == null) {
            return false;
        }
        final List<Long> list = order.get(parser.getNamespace());
        list.remove(id);
        if (list.isEmpty()) {
            order.remove(parser.getNamespace());
        }
        return true;
    }

    @Override
    public boolean has(final long id) {
        return parsers.containsKey(id);
    }

    @Override
    public BlockDataParser get(final long id) {
        return parsers.get(id);
    }

    @Override
    public int getPosition(final long id) {
        final BlockDataParser parser = get(id);
        if (parser == null) {
            return -1;
        }
        final List<Long> list = order.get(parser.getNamespace());
        return list.size() - list.indexOf(id);
    }

    @Override
    public IBlockData parse(final BlockStateEditor editor) {
        Objects.requireNonNull(editor, "BlockStateEditor can't be null!");
        final NbtCompound compound = new NbtCompound();
        compound.set("namespace", editor.getNamespace());
        compound.set("id", editor.getId());
        final NbtCompound statesTag = new NbtCompound();
        final HashMap<String, String> states = editor.getStates();
        for (final Entry<String, String> entry : states.entrySet()) {
            statesTag.set(entry.getKey(), entry.getValue());
        }
        compound.set("states", statesTag);
        if (editor.hasProperties()) {
            final JsonObject object = JsonIO.parseProperties(editor.getProperties());
            if (object == null) {
                return parse(compound);
            }
            final NbtTag tag = JsonIO.toNbt(object);
            if (tag == null || tag.getType() != NbtType.COMPOUND || ((NbtCompound) tag).size() == 0) {
                return parse(compound);
            }
            compound.set("properties", tag);
        }
        return parse(compound);
    }

    @Override
    public IBlockData parse(final NbtCompound compound) {
        Objects.requireNonNull(compound, "NbtCompound can't be null!");
        Checks.hasKey(compound, "namespace", NbtType.STRING);
        Checks.hasKey(compound, "id", NbtType.STRING);
        Checks.hasKey(compound, "states", NbtType.COMPOUND);
        final List<Long> list = order.get(compound.getString("namespace"));
        if (list == null) {
            logger.log(LogTypeId.WARNING, "Can't parse namespace '" + compound.getString("namespace") + "', no BlockDataParser available!");
            return null;
        }
        final int size = list.size();
        for (int index = size - 1; index >= 0; index--) {
            final IBlockData output = parsers.get(list.get(index)).parse(access, compound);
            if (output != null) {
                return output;
            }
        }
        return null;
    }

}
