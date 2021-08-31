package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.schematic.SchematicConverter;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilitySchematicConverter extends SchematicConverter {

    protected final CompatibilityAddon addon;

    public CompatibilitySchematicConverter(@NonNull RealisticWorldGenerator api, @NonNull CompatibilityAddon addon, String... extensions) {
        super(Objects.requireNonNull(api, "RealisticWorldGenerator api can't be null!").getLogger(), Objects.requireNonNull(addon, "CompatibilityAddon can't be null!").getOwner(), extensions);
        this.addon = addon;
        Preconditions.checkArgument(api.getCompatibilityManager().register(this), "Failed to register CompatibilitySchematicConverter");
    }

    @NonNull
    public final CompatibilityAddon getAddon() {
        return addon;
    }

}
