package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import java.io.DataInput;
import java.io.DataOutput;

import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.InstanceBuilder;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;

public final class GlobalLookup {

    private GlobalLookup() {}

    public static void setup(final AccessorProvider provider, final VersionProvider version) {

        /*
         * Setup everything else
         */

        final Class<?> nbtTagCompoundClass = provider.create("nms_nbt_compound", version.minecraftClass("NBTTagCompound")).getOwner();
        final Class<?> nmsItemStackClass = provider.create("nms_itemstack", version.minecraftClass("ItemStack"))
            .findMethod("save", "save", nbtTagCompoundClass).findMethod("load", "a", nbtTagCompoundClass).getOwner();

        final Class<?> nbtBaseClass = version.minecraftClass("NBTBase");
        final Class<?> nbtReadLimiterClass = version.minecraftClass("NBTReadLimiter");

        provider.create("nms_nbt_read_limiter", nbtReadLimiterClass).findField("limiter", "a");

        provider.create("nms_minecraft_key", version.minecraftClass("MinecraftKey")).findMethod("key", "getKey");
        provider.create("nms_area_transformer_8", version.minecraftClass("AreaTransformer8")).findMethod("apply", "apply", int.class,
            int.class);
        provider.create("nms_chunk_generator", version.minecraftClass("ChunkGenerator")).findMethod("worldChunkManager",
            "getWorldChunkManager");

        provider.create("nms_world_server", version.minecraftClass("WorldServer")).findMethod("chunkProvider", "getChunkProvider");
        provider.create("nms_chunk_provider_server", version.minecraftClass("ChunkProviderServer")).findMethod("generator",
            "getChunkGenerator");
        provider.create("nms_entity_skull", version.minecraftClass("TileEntitySkull")).findMethod("profile", "setGameProfile",
            GameProfile.class);
        provider.create("nms_entity_spawner", version.minecraftClass("TileEntityMobSpawner"))
            .findMethod("save", "save", nbtTagCompoundClass).findMethod("load", "load", nbtTagCompoundClass);

        provider.create("nms_stream_tools", version.minecraftClass("NBTCompressedStreamTools"))
            .findMethod("read", "a", DataInput.class, int.class, nbtReadLimiterClass)
            .findMethod("write", "a", nbtBaseClass, DataOutput.class);

        provider.create("cb_world", version.craftBukkitClass("CraftWorld")).findMethod("handle", "getHandle").findField("generator",
            "generator");
        provider.create("cb_generator", version.craftBukkitClass("generator.CustomChunkGenerator")).findField("generator", "generator");
        provider.create("cb_block_entityState", version.craftBukkitClass("block.CraftBlockEntityState")).findMethod("entity",
            "getTileEntity");
        provider.create("cb_block_skull", version.craftBukkitClass("block.CraftSkull")).findField("profile", "profile");
        provider.create("cb_itemstack", version.craftBukkitClass("inventory.CraftItemStack"))
            .findMethod("nms", "asNMSCopy", ItemStack.class).findMethod("bukkit", "asBukkitCopy", nmsItemStackClass);

        provider.create("cb_skull_meta", version.craftBukkitClass("inventory.CraftMetaSkull")).findField("profile", "profile");

        /*
         * Setup version related stuff
         */

        versionSetup(provider, version);
    }

    private static void versionSetup(final AccessorProvider provider, final VersionProvider version) {
        final VersionLookup lookup = InstanceBuilder.buildExact(VersionLookup.class).orElse(null);
        if (lookup == null) {
            return;
        }
        lookup.setup(provider, version);
    }

}
