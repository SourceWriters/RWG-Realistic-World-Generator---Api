package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Optional;

import org.bukkit.inventory.ItemStack;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtDeserializer;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtSerializer;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.Accessor;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;

public final class NmsNbtAccessImpl implements INmsNbtAccess {

    private final AccessorProvider provider;
    private final ILogger logger;

    public NmsNbtAccessImpl(final AccessorProvider provider, final ILogger logger) {
        this.provider = provider;
        this.logger = logger;
    }

    @Override
    public NbtCompound itemToCompound(final ItemStack itemStack) {

        final Optional<Accessor> option0 = provider.get("cb_itemstack");
        final Optional<Accessor> option1 = provider.get("nms_itemstack");
        final Optional<Accessor> option2 = provider.get("nms_nbt_compound");
        if (!option0.isPresent() || !option1.isPresent() || !option2.isPresent()) {
            return null;
        }

        final Accessor cbStack = option0.get();
        final Accessor nmsStack = option1.get();
        final Accessor nbt = option2.get();

        Object nbtCompound = nbt.initialize();

        final Object item = cbStack.invoke("nms", itemStack);
        nbtCompound = nmsStack.invoke(item, "save", nbtCompound);

        return (NbtCompound) fromMinecraftTag(nbtCompound);

    }

    @Override
    public ItemStack itemFromCompound(final NbtCompound compound) {

        final Optional<Accessor> option0 = provider.get("cb_itemstack");
        final Optional<Accessor> option1 = provider.get("nms_itemstack");
        if (!option0.isPresent() || !option1.isPresent()) {
            return null;
        }

        final Accessor cbStack = option0.get();
        final Accessor nmsStack = option1.get();

        final Object nmsItem = nmsStack.invoke("load", toMinecraftTag(compound));
        final Object bktItem = cbStack.invoke("bukkit", nmsItem);

        return (ItemStack) bktItem;

    }

    @Override
    public NbtTag fromMinecraftTag(final Object nmsTag) {
        try {
            final PipedOutputStream output = new PipedOutputStream();
            final PipedInputStream stream = new PipedInputStream(output);

            provider.getOrNull("nms_stream_tools").invoke("write", nmsTag, new DataOutputStream(output));
            final NbtTag tag = NbtDeserializer.UNCOMPRESSED.fromStream(stream).getTag();

            output.close();
            stream.close();

            return tag;
        } catch (final IOException ex) {
            logger.log(LogTypeId.WARNING, ex);
        }
        return null;
    }

    @Override
    public Object toMinecraftTag(final NbtTag tag) {
        try {

            final PipedInputStream input = new PipedInputStream();
            final PipedOutputStream stream = new PipedOutputStream(input);

            NbtSerializer.UNCOMPRESSED.toStream(new NbtNamedTag("root", tag), stream);

            final Object nbt = provider.getOrNull("nms_stream_tools").invoke("read", new DataInputStream(input), 0,
                provider.getOrNull("nms_nbt_read_limiter").getValue("limiter"));

            input.close();
            stream.close();

            return nbt;

        } catch (final IOException ex) {
            logger.log(LogTypeId.WARNING, ex);
        }
        return null;
    }

}
