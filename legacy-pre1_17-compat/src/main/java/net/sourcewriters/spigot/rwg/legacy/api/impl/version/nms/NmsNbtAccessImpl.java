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

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;

public final class NmsNbtAccessImpl implements INmsNbtAccess {

    private final ClassLookupProvider provider;
    private final ILogger logger;

    public NmsNbtAccessImpl(final ClassLookupProvider provider, final ILogger logger) {
        this.provider = provider;
        this.logger = logger;
    }

    @Override
    public NbtCompound itemToCompound(final ItemStack itemStack) {

        final Optional<ClassLookup> option0 = provider.getOptionalLookup("cb_itemstack");
        final Optional<ClassLookup> option1 = provider.getOptionalLookup("nms_itemstack");
        final Optional<ClassLookup> option2 = provider.getOptionalLookup("nms_nbt_compound");
        if (!option0.isPresent() || !option1.isPresent() || !option2.isPresent()) {
            return null;
        }

        final ClassLookup cbStack = option0.get();
        final ClassLookup nmsStack = option1.get();
        final ClassLookup nbt = option2.get();

        Object nbtCompound = nbt.init();

        final Object item = cbStack.run("nms", itemStack);
        nbtCompound = nmsStack.run(item, "save", nbtCompound);

        return (NbtCompound) fromMinecraftTag(nbtCompound);

    }

    @Override
    public ItemStack itemFromCompound(final NbtCompound compound) {

        final Optional<ClassLookup> option0 = provider.getOptionalLookup("cb_itemstack");
        final Optional<ClassLookup> option1 = provider.getOptionalLookup("nms_itemstack");
        if (!option0.isPresent() || !option1.isPresent()) {
            return null;
        }

        final ClassLookup cbStack = option0.get();
        final ClassLookup nmsStack = option1.get();

        final Object nmsItem = nmsStack.run("load", toMinecraftTag(compound));
        final Object bktItem = cbStack.run("bukkit", nmsItem);

        return (ItemStack) bktItem;

    }

    @Override
    public NbtTag fromMinecraftTag(final Object nmsTag) {
        try {
            final PipedOutputStream output = new PipedOutputStream();
            final PipedInputStream stream = new PipedInputStream(output);

            provider.getLookup("nms_stream_tools").execute("write", nmsTag, new DataOutputStream(output));
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

            final Object nbt = provider.getLookup("nms_stream_tools").run("read", new DataInputStream(input), 0,
                provider.getLookup("nms_nbt_read_limiter").getFieldValue("limiter"));

            input.close();
            stream.close();

            return nbt;

        } catch (final IOException ex) {
            logger.log(LogTypeId.WARNING, ex);
        }
        return null;
    }

}
