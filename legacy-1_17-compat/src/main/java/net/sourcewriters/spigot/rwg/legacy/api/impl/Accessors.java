package net.sourcewriters.spigot.rwg.legacy.api.impl;

import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_17_R1.generator.CustomChunkGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.Accessor;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;

public final class Accessors {

    private static final VersionProvider PROVIDER = VersionProvider.get();

    public static final Accessor CUSTOM_CHUNK_GENERATOR = Accessor.of(CustomChunkGenerator.class).findField("generator", "generator");
    public static final Accessor CRAFT_WORLD = Accessor.of(CraftWorld.class).findField("generator", "generator");
    public static final Accessor CRAFT_BLOCK_ENTITY_STATE = Accessor.of(CraftBlockEntityState.class).findField("entity", "getTileEntity");
    public static final Accessor CRAFT_META_SKULL = PROVIDER.craftBukkitAccess("inventory.CraftMetaSkull").findField("profile", "profile");

}
