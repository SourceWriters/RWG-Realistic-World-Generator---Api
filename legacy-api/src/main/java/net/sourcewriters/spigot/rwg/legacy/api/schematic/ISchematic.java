package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

@Unsafe(status = UnsafeStatus.SUBJECT_TO_CHANGE, useable = true)
public interface ISchematic {
    
    String getName();

}
