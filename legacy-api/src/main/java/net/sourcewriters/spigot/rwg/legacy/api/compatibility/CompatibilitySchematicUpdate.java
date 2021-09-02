package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.schematic.ISchematic;
import net.sourcewriters.spigot.rwg.legacy.api.schematic.update.SchematicUpdate;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public abstract class CompatibilitySchematicUpdate<T extends ISchematic> extends SchematicUpdate<T> {

    protected final CompatibilityAddon addon;

    public CompatibilitySchematicUpdate(@NonNull RealisticWorldGenerator api, @NonNull CompatibilityAddon addon, MinecraftVersion version,
        Class<T> type) {
        this(api, addon, version, type, false);
    }

    public CompatibilitySchematicUpdate(@NonNull RealisticWorldGenerator api, @NonNull CompatibilityAddon addon, MinecraftVersion version,
        Class<T> type, boolean preLoad) {
        super(Objects.requireNonNull(addon, "CompatibilityAddon can't be null!").getOwner(), version, type, preLoad);
        this.addon = addon;
        Objects.requireNonNull(api, "RealisticWorldGenerator api can't be null!");
        Preconditions.checkArgument(api.getCompatibilityManager().register(this), "Failed to register CompatibilitySchematicUpdater");
    }

    @NonNull
    public final CompatibilityAddon getAddon() {
        return addon;
    }

}
