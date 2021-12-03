package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_18_R1.generator.CustomChunkGenerator;
import org.bukkit.generator.ChunkGenerator;

import com.mojang.authlib.GameProfile;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class GlobalLookup {

    private GlobalLookup() {}

    public static void setup(final ClassLookupProvider provider) {

        provider.createLookup("cb_world", CraftWorld.class).searchField("generator", "generator", ChunkGenerator.class);
        provider.createLookup("cb_generator", CustomChunkGenerator.class).searchField("generator", "generator", ChunkGenerator.class);

        provider.createLookup("cb_block_entity_state", CraftBlockEntityState.class).searchMethod("entity", "getTileEntity");

        provider.createLookup("cb_skull_meta", provider.getCBClass("inventory.CraftMetaSkull")).searchField("profile", "profile",
            GameProfile.class);

    }

}
