package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class VersionLookup1_15 extends VersionLookup {

    public VersionLookup1_15() {}

    @Override
    public void setup(final ClassLookupProvider provider) {

        final Class<?> iRegistryClass = provider.getNMSClass("IRegistry");
        final Class<?> genLayerClass = provider.getNMSClass("GenLayer");
        final Class<?> areaLazyClass = provider.getNMSClass("AreaLazy");
        final Class<?> areaTransformer8Class = provider.getNMSClass("AreaTransformer8");

        provider.createLookup("nms_world_chunk_manager_overworld", provider.getNMSClass("WorldChunkManagerOverworld"))
            .searchField("genLayer", "d", genLayerClass);
        provider.createLookup("nms_gen_layer", genLayerClass).searchField("areaLazy", "b", areaLazyClass);
        provider.createLookup("nms_area_lazy", areaLazyClass).searchField("transformer", "a", areaTransformer8Class);

        provider.createLookup("nms_registry", iRegistryClass).searchField("biomeRegistry", "BIOME", iRegistryClass);
        provider.createLookup("nms_registry_materials", provider.getNMSClass("RegistryMaterials")).searchMethod("id", "fromId", int.class)
            .searchMethod("key", "getKey", Object.class);

    }

}
