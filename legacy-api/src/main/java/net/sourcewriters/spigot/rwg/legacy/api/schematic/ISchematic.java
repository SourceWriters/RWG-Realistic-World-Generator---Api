package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

@Unsafe(status = UnsafeStatus.SUBJECT_TO_CHANGE, useable = true)
public interface ISchematic {

    String getName();

    MinecraftVersion getFormatVersion();

}
