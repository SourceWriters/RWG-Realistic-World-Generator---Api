package net.sourcewriters.spigot.rwg.legacy.api.impl.nms;


import org.bukkit.World;
import org.bukkit.block.Biome;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public final class NmsBiomeAccessImpl implements INmsBiomeAccess {

//    private final ClassLookupProvider provider;

    public NmsBiomeAccessImpl(ClassLookupProvider provider) {
//        this.provider = provider;
    }

    @Override
    public Biome getBiomeAt(World bukkitWorld, int x, int y, int z) {
        
        // TODO: 
        // Find method to get a biome at a specific location in the world
        // via nms access in mojang mappings
        
        return Biome.THE_VOID;
    }

}
