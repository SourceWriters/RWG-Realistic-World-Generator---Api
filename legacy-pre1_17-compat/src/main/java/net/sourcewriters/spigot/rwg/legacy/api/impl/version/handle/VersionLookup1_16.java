package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public final class VersionLookup1_16 extends VersionLookup {

    public VersionLookup1_16() {}

    @Override
    public void setup(final ClassLookupProvider provider) {

        final Class<?> nbtTagCompoundClass = provider.getLookup("nms_nbt_compound").getOwner();
        final Class<?> iBlockDataClass = provider.createLookup("nms_blockdata", provider.getNMSClass("IBlockData")).getOwner();

        final Class<?> iRegistryClass = provider.getNMSClass("IRegistry");
        final Class<?> genLayerClass = provider.getNMSClass("GenLayer");
        final Class<?> areaLazyClass = provider.getNMSClass("AreaLazy");
        final Class<?> areaTransformer8Class = provider.getNMSClass("AreaTransformer8");
        provider.createLookup("nms_world_chunk_manager_overworld", provider.getNMSClass("WorldChunkManagerOverworld"))
            .searchField("genLayer", "f", genLayerClass);
        provider.createLookup("nms_gen_layer", genLayerClass).searchField("areaLazy", "b", areaLazyClass);
        provider.createLookup("nms_area_lazy", areaLazyClass).searchField("transformer", "a", areaTransformer8Class);

        if (Versions.isServerCompat(1, 16, 2)) {
            provider.createLookup("nms_biome_registry", provider.getNMSClass("BiomeRegistry")).searchMethod("id", "a", int.class);
            provider.createLookup("nms_resource_key", provider.getNMSClass("ResourceKey")).searchMethod("key", "a");
        } else {
            provider.createLookup("nms_registry", iRegistryClass).searchField("biomeRegistry", "BIOME", iRegistryClass);
            provider.createLookup("nms_registry_materials", provider.getNMSClass("RegistryMaterials"))
                .searchMethod("id", "fromId", int.class).searchMethod("key", "getKey", Object.class);
        }

        provider.getLookup("nms_entity_spawner").searchMethod("load", "load", iBlockDataClass, nbtTagCompoundClass);

    }

}