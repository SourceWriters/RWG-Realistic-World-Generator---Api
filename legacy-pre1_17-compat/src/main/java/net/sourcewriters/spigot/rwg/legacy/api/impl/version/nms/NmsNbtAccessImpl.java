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

    public NmsNbtAccessImpl(ClassLookupProvider provider, ILogger logger) {
        this.provider = provider;
        this.logger = logger;
    }

    @Override
    public NbtCompound itemToCompound(ItemStack itemStack) {

        Optional<ClassLookup> option0 = provider.getOptionalLookup("cb_itemstack");
        Optional<ClassLookup> option1 = provider.getOptionalLookup("nms_itemstack");
        Optional<ClassLookup> option2 = provider.getOptionalLookup("nms_nbt_compound");
        if (!option0.isPresent() || !option1.isPresent() || !option2.isPresent()) {
            return null;
        }

        ClassLookup cbStack = option0.get();
        ClassLookup nmsStack = option1.get();
        ClassLookup nbt = option2.get();

        Object nbtCompound = nbt.init();

        Object item = cbStack.run("nms", itemStack);
        nbtCompound = nmsStack.run(item, "save", nbtCompound);

        return (NbtCompound) fromMinecraftTag(nbtCompound);

    }

    @Override
    public ItemStack itemFromCompound(NbtCompound compound) {

        Optional<ClassLookup> option0 = provider.getOptionalLookup("cb_itemstack");
        Optional<ClassLookup> option1 = provider.getOptionalLookup("nms_itemstack");
        if (!option0.isPresent() || !option1.isPresent()) {
            return null;
        }

        ClassLookup cbStack = option0.get();
        ClassLookup nmsStack = option1.get();

        Object nmsItem = nmsStack.run("load", toMinecraftTag(compound));
        Object bktItem = cbStack.run("bukkit", nmsItem);

        return (ItemStack) bktItem;

    }

    @Override
    public NbtTag fromMinecraftTag(Object nmsTag) {
        try {
            PipedOutputStream output = new PipedOutputStream();
            PipedInputStream stream = new PipedInputStream(output);

            provider.getLookup("nms_stream_tools").execute("write", nmsTag, new DataOutputStream(output));
            NbtTag tag = NbtDeserializer.UNCOMPRESSED.fromStream(stream).getTag();

            output.close();
            stream.close();

            return tag;
        } catch (IOException ex) {
            logger.log(LogTypeId.WARNING, ex);
        }
        return null;
    }

    @Override
    public Object toMinecraftTag(NbtTag tag) {
        try {

            PipedInputStream input = new PipedInputStream();
            PipedOutputStream stream = new PipedOutputStream(input);

            NbtSerializer.UNCOMPRESSED.toStream(new NbtNamedTag("root", tag), stream);

            Object nbt = provider.getLookup("nms_stream_tools").run("read", new DataInputStream(input), 0,
                provider.getLookup("nms_nbt_read_limiter").getFieldValue("limiter"));

            input.close();
            stream.close();

            return nbt;

        } catch (IOException ex) {
            logger.log(LogTypeId.WARNING, ex);
        }
        return null;
    }

}
