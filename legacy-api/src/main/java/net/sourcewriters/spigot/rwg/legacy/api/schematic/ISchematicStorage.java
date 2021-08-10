package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;

@Unsafe
public interface ISchematicStorage {
    
    @NonNull
    public ISchematic create(ISchematicBuilder builder);

    public boolean add(ISchematic schematic);

    public ISchematic get(String name);

    public boolean has(String name);
    
    @NonNull
    public String[] getNames();
    
    @NonNull
    public ISchematic[] getSchematics();

}
