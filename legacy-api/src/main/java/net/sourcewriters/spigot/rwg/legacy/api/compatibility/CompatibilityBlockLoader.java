package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataLoader;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilityBlockLoader extends BlockDataLoader {

    protected final CompatibilityAddon addon;

    public CompatibilityBlockLoader(@NonNull final RealisticWorldGenerator api, @NonNull final CompatibilityAddon addon,
        @NonNull final String name) {
        super(Objects.requireNonNull(addon, "CompatibilityAddon can't be null!").getOwner(), name);
        Objects.requireNonNull(api, "RealisticWorldGenerator api can't be null!");
        this.addon = addon;
        Preconditions.checkArgument(api.getCompatibilityManager().register(this), "Failed to register CompatibilityBlockLoader");
    }

    @NonNull
    public final CompatibilityAddon getAddon() {
        return addon;
    }

}
