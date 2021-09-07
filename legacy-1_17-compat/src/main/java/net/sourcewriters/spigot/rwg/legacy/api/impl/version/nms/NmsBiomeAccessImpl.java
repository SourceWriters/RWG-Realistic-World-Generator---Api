package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public final class NmsBiomeAccessImpl implements INmsBiomeAccess {

    public NmsBiomeAccessImpl(ClassLookupProvider _ignore) {}

    @Override
    public org.bukkit.block.Biome getBiomeAt(World bukkitWorld, int x, int y, int z) {
        if (bukkitWorld == null) {
            return org.bukkit.block.Biome.THE_VOID;
        }
        ServerLevel server = ((CraftWorld) bukkitWorld).getHandle();
        BiomeManager manager = server.getBiomeManager();
        Biome biome = manager.getNoiseBiomeAtPosition(new BlockPos(x, y, z));
        if (biome == null) {
            return org.bukkit.block.Biome.THE_VOID;
        }
        ResourceLocation location = server.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome);
        if (location == null) {
            return org.bukkit.block.Biome.THE_VOID;
        }
        org.bukkit.block.Biome bktBiome = org.bukkit.Registry.BIOME.get(NamespacedKey.minecraft(location.getPath()));
        return bktBiome == null ? org.bukkit.block.Biome.THE_VOID : bktBiome;
    }

}
