package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import java.io.DataInput;
import java.io.DataOutput;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.InstanceBuilder;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class GlobalLookup {

    private GlobalLookup() {}

    public static void setup(final ClassLookupProvider provider) {

        /*
         * Setup everything else
         */

        final Class<?> nbtTagCompoundClass = provider.createLookup("nms_nbt_compound", provider.getNMSClass("NBTTagCompound")).getOwner();
        final Class<?> nmsItemStackClass = provider.createLookup("nms_itemstack", provider.getNMSClass("ItemStack"))
            .searchMethod("save", "save", nbtTagCompoundClass).searchMethod("load", "a", nbtTagCompoundClass).getOwner();

        final Class<?> nbtBaseClass = provider.getNMSClass("NBTBase");
        final Class<?> nbtReadLimiterClass = provider.getNMSClass("NBTReadLimiter");

        provider.createLookup("nms_nbt_read_limiter", nbtReadLimiterClass).searchField("limiter", "a", nbtReadLimiterClass);

        provider.createLookup("nms_minecraft_key", provider.getNMSClass("MinecraftKey")).searchMethod("key", "getKey");
        provider.createLookup("nms_area_transformer_8", provider.getNMSClass("AreaTransformer8")).searchMethod("apply", "apply", int.class,
            int.class);
        provider.createLookup("nms_chunk_generator", provider.getNMSClass("ChunkGenerator")).searchMethod("worldChunkManager",
            "getWorldChunkManager");

        provider.createLookup("nms_world_server", provider.getNMSClass("WorldServer")).searchMethod("chunkProvider", "getChunkProvider");
        provider.createLookup("nms_chunk_provider_server", provider.getNMSClass("ChunkProviderServer")).searchMethod("generator",
            "getChunkGenerator");
        provider.createLookup("nms_entity_skull", provider.getNMSClass("TileEntitySkull")).searchMethod("profile", "setGameProfile",
            GameProfile.class);
        provider.createLookup("nms_entity_spawner", provider.getNMSClass("TileEntityMobSpawner"))
            .searchMethod("save", "save", nbtTagCompoundClass).searchMethod("load", "load", nbtTagCompoundClass);

        provider.createLookup("nms_stream_tools", provider.getNMSClass("NBTCompressedStreamTools"))
            .searchMethod("read", "a", DataInput.class, int.class, nbtReadLimiterClass)
            .searchMethod("write", "a", nbtBaseClass, DataOutput.class);

        provider.createLookup("cb_world", provider.getCBClass("CraftWorld")).searchMethod("handle", "getHandle").searchField("generator",
            "generator", ChunkGenerator.class);
        provider.createLookup("cb_generator", provider.getCBClass("generator.CustomChunkGenerator")).searchField("generator", "generator",
            ChunkGenerator.class);
        provider.createLookup("cb_block_entityState", provider.getCBClass("block.CraftBlockEntityState")).searchMethod("entity",
            "getTileEntity");
        provider.createLookup("cb_block_skull", provider.getCBClass("block.CraftSkull")).searchField("profile", "profile",
            GameProfile.class);
        provider.createLookup("cb_itemstack", provider.getCBClass("inventory.CraftItemStack"))
            .searchMethod("nms", "asNMSCopy", ItemStack.class).searchMethod("bukkit", "asBukkitCopy", nmsItemStackClass);

        provider.createLookup("cb_skull_meta", provider.getCBClass("inventory.CraftSkullMeta")).searchField("profile", "profile", GameProfile.class);

        /*
         * Setup version related stuff
         */

        versionSetup(provider);
    }

    private static void versionSetup(final ClassLookupProvider provider) {
        final VersionLookup lookup = InstanceBuilder.buildExact(VersionLookup.class).orElse(null);
        if (lookup == null) {
            return;
        }
        lookup.setup(provider);
    }

}
