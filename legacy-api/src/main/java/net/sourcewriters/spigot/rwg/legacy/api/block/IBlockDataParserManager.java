package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.function.Supplier;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBlockDataParserManager {

    boolean register(@NonNull BlockDataParser parser);

    default boolean register(final boolean expression, @NonNull final Supplier<BlockDataParser> parser) {
        if (!expression) {
            return false;
        }
        return register(parser.get());
    }

    boolean unregister(long id);

    boolean has(long id);

    BlockDataParser get(long id);

    int getPosition(long id);

    IBlockData parse(@NonNull BlockStateEditor editor);

    IBlockData parse(@NonNull NbtCompound compound);

}
