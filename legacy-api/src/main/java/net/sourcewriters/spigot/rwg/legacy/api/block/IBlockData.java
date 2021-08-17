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

    default IBlockData setConversionPossible(boolean state) {
        IProperties properties = getProperties();
        if (state) {
            properties.remove("data_conversion");
            return this;
        }
        properties.set(IProperty.of("data_conversion", false));
        return this;
    }

    default boolean isConversionPossible() {
        IProperty<Boolean> property = getProperties().find("data_conversion").cast(boolean.class);
        return property.isPresent() ? property.getValue() : true;
    }

    default boolean isSame(Object obj) {
        return (obj instanceof IBlockData) ? isSame((IBlockData) obj) : false;
    }

    default boolean isSame(IBlockData data) {
        return data != null ? data.asString().equals(asString()) : false;
    }

}
