package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;

public final class Lookup1_15 extends VersionLookup {

    Lookup1_15() {}

    @Override
    public void setup(ClassLookupProvider provider) {

        Class<?> iRegistryClass = provider.getNMSClass("IRegistry");
        Class<?> genLayerClass = provider.getNMSClass("GenLayer");
        Class<?> areaLazyClass = provider.getNMSClass("AreaLazy");
        Class<?> areaTransformer8Class = provider.getNMSClass("AreaTransformer8");

        provider.createLookup("nms_world_chunk_manager_overworld", provider.getNMSClass("WorldChunkManagerOverworld"))
            .searchField("genLayer", "d", genLayerClass);
        provider.createLookup("nms_gen_layer", genLayerClass).searchField("areaLazy", "b", areaLazyClass);
        provider.createLookup("nms_area_lazy", areaLazyClass).searchField("transformer", "a", areaTransformer8Class);

        provider.createLookup("nms_registry", iRegistryClass).searchField("biomeRegistry", "BIOME", iRegistryClass);
        provider.createLookup("nms_registry_materials", provider.getNMSClass("RegistryMaterials")).searchMethod("id", "fromId", int.class)
            .searchMethod("key", "getKey", Object.class);

    }

}
