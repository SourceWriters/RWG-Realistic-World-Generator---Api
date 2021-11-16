package net.sourcewriters.spigot.rwg.legacy.api.schematic.update;

import net.sourcewriters.spigot.rwg.legacy.api.schematic.ISchematic;

public interface ISchematicUpdater {

    boolean register(SchematicUpdate<?> update);

    boolean unregister(long id);

    boolean hasUpdates(Class<?> type);

    <T extends ISchematic> boolean apply(T schematic, boolean preLoad);

}
