package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import java.util.Objects;

import com.google.common.base.Preconditions;

import net.sourcewriters.spigot.rwg.legacy.api.RealisticWorldGenerator;
import net.sourcewriters.spigot.rwg.legacy.api.block.BlockDataParser;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class CompatibilityBlockParser extends BlockDataParser {

    protected final CompatibilityAddon addon;

    public CompatibilityBlockParser(@NonNull RealisticWorldGenerator api, @NonNull CompatibilityAddon addon, @NonNull String namespace) {
        super(Objects.requireNonNull(addon, "CompatibilityAddon can't be null!").getOwner(), namespace);
        Objects.requireNonNull(api, "RealisticWorldGenerator api can't be null!");
        this.addon = addon;
        Preconditions.checkArgument(api.getCompatibilityManager().register(this), "Failed to register CompatibilityBlockParser");
    }

    @NonNull
    public final CompatibilityAddon getAddon() {
        return addon;
    }

}
