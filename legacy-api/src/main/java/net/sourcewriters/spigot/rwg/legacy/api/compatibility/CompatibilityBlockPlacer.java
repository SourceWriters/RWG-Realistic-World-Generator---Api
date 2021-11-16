package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataPlacer;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilityBlockPlacer extends BlockDataPlacer {

    protected final CompatibilityAddon addon;

    public CompatibilityBlockPlacer(@NonNull final RealisticWorldGenerator api, @NonNull final CompatibilityAddon addon,
        @NonNull final String namespace) {
        super(Objects.requireNonNull(addon, "CompatibilityAddon can't be null!").getOwner(), namespace);
        Objects.requireNonNull(api, "RealisticWorldGenerator api can't be null!");
        this.addon = addon;
        Preconditions.checkArgument(api.getCompatibilityManager().register(this), "Failed to register CompatibilityBlockPlacer");
    }

    @NonNull
    public final CompatibilityAddon getAddon() {
        return addon;
    }

}
