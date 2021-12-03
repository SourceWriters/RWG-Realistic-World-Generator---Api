package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Skull;
import org.bukkit.entity.EntityType;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.sourcewriters.spigot.rwg.legacy.api.util.minecraft.ProfileCache;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGEntityType;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;

public final class NmsWorldAccessImpl implements INmsWorldAccess {

    private final ClassLookupProvider provider;

    public NmsWorldAccessImpl(final ClassLookupProvider provider) {
        this.provider = provider;
    }

    @Override
    public void setSpawner(final Block block, final EntityType type, final short spawnCount, final short spawnRange, final short delay,
        final short maxDelay, final short minDelay, final short maxNearbyEntities, final short requiredPlayerRange) {
        final BlockState blockState = block.getState();
        if (!(blockState instanceof CreatureSpawner)) {
            return;
        }
        final SpawnerBlockEntity entity = (SpawnerBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity");
        final CompoundTag tag = entity.getUpdateTag();
        tag.putShort("SpawnCount", spawnCount);
        tag.putShort("SpawnRange", spawnRange);
        tag.putShort("Delay", delay);
        tag.putShort("MinSpawnDelay", maxDelay);
        tag.putShort("MaxSpawnDelay", minDelay);
        tag.putShort("MaxNearbyEntities", maxNearbyEntities);
        tag.putShort("RequiredPlayerRange", requiredPlayerRange);
        final CompoundTag data = new CompoundTag();
        data.putString("id", "minecraft:" + RWGEntityType.toMinecraft(type));
        tag.put("SpawnData", data);
        entity.load(data);
    }

    @Override
    public void setHeadTexture(final Block block, final String texture) {
        final BlockState blockState = block.getState();
        if (!(blockState instanceof Skull)) {
            return;
        }
        final GameProfile gameProfile = ProfileCache.asProfile(texture);
        final SkullBlockEntity entity = (SkullBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity");
        entity.setOwner(gameProfile);
    }

    @Override
    public String getHeadTexture(final Block block) {
        final BlockState blockState = block.getState();
        if (!(blockState instanceof Skull)) {
            return null;
        }
        return ProfileCache
            .asTexture(((SkullBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity")).getOwnerProfile());
    }

}
