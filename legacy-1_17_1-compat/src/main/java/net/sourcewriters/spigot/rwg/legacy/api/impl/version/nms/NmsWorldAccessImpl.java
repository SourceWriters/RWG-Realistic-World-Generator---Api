package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Skull;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.ChunkGenerator;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;
import net.sourcewriters.spigot.rwg.legacy.api.util.minecraft.ProfileCache;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGEntityType;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public final class NmsWorldAccessImpl implements INmsWorldAccess {

    private final ClassLookupProvider provider;
    private final ILogger logger;

    public NmsWorldAccessImpl(ILogger logger, ClassLookupProvider provider) {
        this.provider = provider;
        this.logger = logger;
    }

    @Override
    public void setSpawner(Block block, EntityType type, short spawnCount, short spawnRange, short delay, short maxDelay, short minDelay,
        short maxNearbyEntities, short requiredPlayerRange) {
        BlockState blockState = block.getState();
        if (!(blockState instanceof CreatureSpawner)) {
            return;
        }
        SpawnerBlockEntity entity = (SpawnerBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity");
        CompoundTag tag = entity.getUpdateTag();
        tag.putShort("SpawnCount", spawnCount);
        tag.putShort("SpawnRange", spawnRange);
        tag.putShort("Delay", delay);
        tag.putShort("MinSpawnDelay", maxDelay);
        tag.putShort("MaxSpawnDelay", minDelay);
        tag.putShort("MaxNearbyEntities", maxNearbyEntities);
        tag.putShort("RequiredPlayerRange", requiredPlayerRange);
        CompoundTag data = new CompoundTag();
        data.putString("id", "minecraft:" + RWGEntityType.toMinecraft(type));
        tag.put("SpawnData", data);
        entity.load(data);
    }

    @Override
    public void setGenerator(World world, ChunkGenerator generator) {
        if (generator == null || world == null) {
            return;
        }
        logger.log("Couldn't inject generator '" + generator.getClass().getName() + "' into world '" + world.getName()
            + "' because there is no method to do so in 1.17+ (cause Java 16)");
//      ServerLevel server = (ServerLevel) provider.getLookup("cb_world").run(world, "handle");
//      ServerChunkCache source = (ServerChunkCache) server.getChunkSource();
//      Object chunkGenerator = source.getGenerator();
//      provider.getLookup("cb_generator").setFieldValue(chunkGenerator, "generator", generator);
    }

    @Override
    public void setHeadTexture(Block block, String texture) {
        BlockState blockState = block.getState();
        if (!(blockState instanceof Skull)) {
            return;
        }
        GameProfile gameProfile = ProfileCache.asProfile(texture);
        SkullBlockEntity entity = (SkullBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity");
        entity.setOwner(gameProfile);
    }

    @Override
    public String getHeadTexture(Block block) {
        BlockState blockState = block.getState();
        if (!(blockState instanceof Skull)) {
            return null;
        }
        return ProfileCache
            .asTexture(((SkullBlockEntity) provider.getLookup("cb_block_entity_state").run(blockState, "entity")).getOwnerProfile());
    }

}
