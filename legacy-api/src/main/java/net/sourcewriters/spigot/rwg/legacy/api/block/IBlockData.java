package net.sourcewriters.spigot.rwg.legacy.api.block;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperties;
import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperty;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IBlockData {

    @NonNull
    String getNamespace();

    @NonNull
    String getId();

    @NonNull
    String getKey();

    @NonNull
    IProperties getProperties();

    @NonNull
    String asString();

    @NonNull
    Material getMaterial();

    @NonNull
    BlockData asBukkit();

    @NonNull
    BlockStateEditor asEditor();
    
    default boolean isMinecraft() {
        return getNamespace().equalsIgnoreCase("minecraft");
    }

    default boolean isSyncNeeded() {
        IProperty<Boolean> property = getProperties().find("sync").cast(boolean.class);
        return property.isPresent() ? property.getValue() : false;
    }

}
