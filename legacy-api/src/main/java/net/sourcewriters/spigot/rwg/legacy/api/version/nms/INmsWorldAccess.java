package net.sourcewriters.spigot.rwg.legacy.api.version.nms;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface INmsWorldAccess {

    void setSpawner(@NonNull Block block, @NonNull EntityType type, short spawnCount, short spawnRange, short delay, short maxDelay,
        short minDelay, short maxNearbyEntities, short requiredPlayerRange);

    void setHeadTexture(@NonNull Block block, @NonNull String texture);

    String getHeadTexture(@NonNull Block block);

}
