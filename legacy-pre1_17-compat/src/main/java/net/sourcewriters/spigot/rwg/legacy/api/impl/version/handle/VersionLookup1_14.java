package net.sourcewriters.spigot.rwg.legacy.api.impl.version.handle;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.AccessorProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.provider.VersionProvider;

public final class VersionLookup1_14 extends VersionLookup {

    public VersionLookup1_14() {}

    @Override
    public void setup(final AccessorProvider provider, final VersionProvider version) {

        final Class<?> iRegistryClass = version.minecraftClass("IRegistry");
        final Class<?> genLayerClass = version.minecraftClass("GenLayer");
        final Class<?> areaLazyClass = version.minecraftClass("AreaLazy");

        provider.create("nms_world_chunk_manager_overworld", version.minecraftClass("WorldChunkManagerOverworld")).findField("genLayer",
            "d");
        provider.create("nms_gen_layer", genLayerClass).findField("areaLazy", "b");
        provider.create("nms_area_lazy", areaLazyClass).findField("transformer", "a");

        provider.create("nms_registry", iRegistryClass).findField("biomeRegistry", "BIOME");
        provider.create("nms_registry_materials", version.minecraftClass("RegistryMaterials")).findMethod("id", "fromId", int.class)
            .findMethod("key", "getKey", Object.class);

    }

}
