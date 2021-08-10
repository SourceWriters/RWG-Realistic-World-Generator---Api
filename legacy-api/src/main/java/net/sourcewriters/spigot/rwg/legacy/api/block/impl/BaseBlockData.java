package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperties;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class BaseBlockData implements IBlockData {

    protected final IProperties properties = IProperties.create();

    @NonNull
    @Override
    public final IProperties getProperties() {
        return properties;
    }

}
