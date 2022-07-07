package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public final class VersionLookup1_16 extends VersionLookup {

    public VersionLookup1_16() {}

    @Override
    public void setup(final AccessorProvider provider, final VersionProvider version) {

        final Class<?> nbtTagCompoundClass = provider.getOrNull("nms_nbt_compound").getOwner();
        final Class<?> iBlockDataClass = provider.create("nms_blockdata", version.minecraftClass("IBlockData")).getOwner();

        final Class<?> iRegistryClass = version.minecraftClass("IRegistry");
        final Class<?> genLayerClass = version.minecraftClass("GenLayer");
        final Class<?> areaLazyClass = version.minecraftClass("AreaLazy");
        provider.create("nms_world_chunk_manager_overworld", version.minecraftClass("WorldChunkManagerOverworld")).findField("genLayer",
            "f");
        provider.create("nms_gen_layer", genLayerClass).findField("areaLazy", "b");
        provider.create("nms_area_lazy", areaLazyClass).findField("transformer", "a");

        if (Versions.isServerCompat(1, 16, 2)) {
            provider.create("nms_biome_registry", version.minecraftClass("BiomeRegistry")).findMethod("id", "a", int.class);
            provider.create("nms_resource_key", version.minecraftClass("ResourceKey")).findMethod("key", "a");
        } else {
            provider.create("nms_registry", iRegistryClass).findField("biomeRegistry", "BIOME");
            provider.create("nms_registry_materials", version.minecraftClass("RegistryMaterials")).findMethod("id", "fromId", int.class)
                .findMethod("key", "getKey", Object.class);
        }

        provider.getOrNull("nms_entity_spawner").findMethod("load", "load", iBlockDataClass, nbtTagCompoundClass);

    }

}