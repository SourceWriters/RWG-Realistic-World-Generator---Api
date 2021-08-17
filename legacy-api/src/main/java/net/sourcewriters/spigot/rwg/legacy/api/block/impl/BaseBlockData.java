package net.sourcewriters.spigot.rwg.legacy.api.block.impl;

import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperties;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.data.JsonIO;

public abstract class BaseBlockData implements IBlockData {

    protected final IProperties properties = IProperties.create();

    @NonNull
    @Override
    public final IProperties getProperties() {
        return properties;
    }
    
    protected abstract String dataString();
    
    @Override
    public final String asString() {
        return properties.isEmpty() ? dataString() : dataString() + JsonIO.toString(properties);
    }
    
    @Override
    public final boolean equals(Object obj) {
        return isSame(obj);
    }

}
