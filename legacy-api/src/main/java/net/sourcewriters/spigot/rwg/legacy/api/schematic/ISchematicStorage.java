package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import java.util.concurrent.Future;

import org.bukkit.Location;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.schematic.update.ISchematicUpdater;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

@Unsafe(status = UnsafeStatus.SUBJECT_TO_CHANGE, useable = true)
public interface ISchematicStorage {

    ISchematic get(String name);

    boolean has(String name);
    
    int amount();
    
    @NonNull
    String[] getNames();
    
    @NonNull
    ISchematic[] getSchematics();

    @NonNull
    Future<?> paste(ISchematic schmeatic, Location location);

    @NonNull
    Future<?> paste(ISchematic schmeatic, Location location, RandomNumberGenerator random);
    
    @NonNull
    ISchematicLoader getLoader();
    
    @NonNull
    ISchematicUpdater getUpdater();

}
