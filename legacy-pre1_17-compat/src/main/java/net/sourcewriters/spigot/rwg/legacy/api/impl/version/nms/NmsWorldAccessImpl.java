package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import java.util.Optional;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Skull;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.ChunkGenerator;

import com.mojang.authlib.GameProfile;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupCache;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsNbtAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsWorldAccess;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.ServerVersion;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;
import net.sourcewriters.spigot.rwg.legacy.api.util.minecraft.ProfileCache;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGEntityType;

public final class NmsWorldAccessImpl implements INmsWorldAccess {

    private final ClassLookupCache cache;
    private final INmsNbtAccess access;

    public NmsWorldAccessImpl(ClassLookupProvider provider, INmsNbtAccess access) {
        this.cache = provider.getCache();
        this.access = access;
    }

    @Override
    public void setSpawner(Block block, EntityType type, short spawnCount, short spawnRange, short delay, short maxDelay, short minDelay,
        short maxNearbyEntities, short requiredPlayerRange) {

        ServerVersion version = Versions.getServer();

        Optional<ClassLookup> option0 = cache.get("cb_block_entityState");
        Optional<ClassLookup> option1 = cache.get("nms_entity_spawner");
        Optional<ClassLookup> option2 = cache.get("nms_nbt_compound");
        Optional<ClassLookup> option3 = cache.get("nms_blockdata");
        if (!option0.isPresent() || !option1.isPresent() || !option2.isPresent() || (version.getMinor() >= 16 && !option3.isPresent())) {
            return;
        }

        ClassLookup state = option0.get();
        ClassLookup spawner = option1.get();
        ClassLookup nbt = option2.get();
        ClassLookup blockData = option3.orElse(null);

        BlockState blockState = block.getState();
        if (!(blockState instanceof CreatureSpawner)) {
            return;
        }

        Object spawnerObject = state.run(blockState, "entity");

        Object nbtCompound = nbt.init();
        nbtCompound = spawner.run(spawnerObject, "save", nbtCompound);

        NbtCompound compound = (NbtCompound) access.fromMinecraftTag(nbtCompound);

        compound.remove("SpawnPotentials");

        compound.set("SpawnCount", spawnCount);
        compound.set("SpawnRange", spawnRange);
        compound.set("Delay", delay);
        compound.set("MinSpawnDelay", minDelay);
        compound.set("MaxSpawnDelay", maxDelay);
        compound.set("MaxNearbyEntities", maxNearbyEntities);
        compound.set("RequiredPlayerRange", requiredPlayerRange);

        NbtCompound dataCompound = new NbtCompound();
        dataCompound.set("id", "minecraft:" + RWGEntityType.toMinecraft(type));

        compound.set("SpawnData", dataCompound);

        Object spawnerCompound = access.toMinecraftTag(compound);

        if (version.getMinor() >= 16) {
            spawner.execute(spawnerObject, "load", blockData.getOwner().cast(null), spawnerCompound);
        } else {
            spawner.execute(spawnerObject, "load", spawnerCompound);
        }

    }

    @Override
    public void setGenerator(World world, ChunkGenerator generator) {

        Optional<ClassLookup> option0 = cache.get("cb_world");
        Optional<ClassLookup> option1 = cache.get("nms_world_server");
        Optional<ClassLookup> option2 = cache.get("nms_chunk_provider_server");
        Optional<ClassLookup> option3 = cache.get("cb_generator");

        if (!option0.isPresent() || !option1.isPresent() || !option2.isPresent() || !option3.isPresent()) {
            return;
        }

        option0.get().setFieldValue(world, "generator", generator);

        Object worldServer = option0.get().run(world, "handle");
        Object chunkProvider = option1.get().run(worldServer, "chunkProvider");
        Object chunkGenerator = option2.get().run(chunkProvider, "generator");

        option3.get().setFieldValue(chunkGenerator, "generator", generator);

    }

    @Override
    public void setHeadTexture(Block block, String texture) {

        if (texture == null) {
            return;
        }

        Optional<ClassLookup> option0 = cache.get("nms_entity_skull");
        Optional<ClassLookup> option1 = cache.get("cb_block_entityState");
        if (!option0.isPresent() || !option1.isPresent()) {
            return;
        }

        ClassLookup nmsSkull = option0.get();
        ClassLookup cbEntityState = option1.get();

        BlockState state = block.getState();
        if (!(state instanceof Skull)) {
            return;
        }

        GameProfile gameProfile = ProfileCache.asProfile(texture);

        Object tileEntity = cbEntityState.run(state, "entity");
        nmsSkull.execute(tileEntity, "profile", gameProfile);

    }

    @Override
    public String getHeadTexture(Block block) {

        Optional<ClassLookup> option0 = cache.get("cb_block_skull");
        if (!option0.isPresent()) {
            return null;
        }

        ClassLookup skull = option0.get();

        BlockState state = block.getState();
        if (!(state instanceof Skull)) {
            return null;
        }

        return ProfileCache.asTexture((GameProfile) skull.getFieldValue(state, "profile"));
    }

}
