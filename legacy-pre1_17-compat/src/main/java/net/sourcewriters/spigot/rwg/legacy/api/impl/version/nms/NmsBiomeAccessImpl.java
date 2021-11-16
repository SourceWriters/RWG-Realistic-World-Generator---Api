package net.sourcewriters.spigot.rwg.legacy.api.impl.version.nms;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.World;
import org.bukkit.block.Biome;

import com.google.common.hash.Hashing;

import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookup;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupCache;
import net.sourcewriters.spigot.rwg.legacy.api.version.handle.ClassLookupProvider;
import net.sourcewriters.spigot.rwg.legacy.api.version.nms.INmsBiomeAccess;

public final class NmsBiomeAccessImpl implements INmsBiomeAccess {

    private final ConcurrentHashMap<Long, Long> hashCache = new ConcurrentHashMap<>();

    private final ClassLookupCache cache;

    public NmsBiomeAccessImpl(final ClassLookupProvider provider) {
        this.cache = provider.getCache();
    }

    @Override
    public Biome getBiomeAt(final World bukkitWorld, final int x, final int y, final int z) {
        Optional<ClassLookup> option;
        final ClassLookup refCbWorld = get("cb_world");
        final ClassLookup refWorldServer = get("nms_world_server");
        final ClassLookup refChunkProviderServer = get("nms_chunk_provider_server");
        final ClassLookup refChunkGenerator = get("nms_chunk_generator");
        final ClassLookup refWorldChunkManagerOverworld = get("nms_world_chunk_manager_overworld");
        final ClassLookup refGenLayer = get("nms_gen_layer");
        final ClassLookup refAreaLazy = get("nms_area_lazy");
        final ClassLookup refTransformer = get("nms_area_transformer_8");
        option = cache.get("nms_biome_registry");
        ClassLookup refRegistryExt = null;
        ClassLookup refRegistry;
        ClassLookup refMinecraftKeyExt = null;
        if (option.isPresent()) {
            refRegistry = option.get();
            refMinecraftKeyExt = get("nms_resource_key");
        } else {
            refRegistryExt = get("nms_i_registry");
            refRegistry = get("nms_registry_materials");
        }
        final ClassLookup refMinecraftKey = get("nms_minecraft_key");

        final Object worldServer = refCbWorld.run(bukkitWorld, "handle");
        final Object chunkProviderServer = refWorldServer.run(worldServer, "chunkProvider");
        final Object generator = refChunkProviderServer.run(chunkProviderServer, "generator");
        final Object chunkManager = refChunkGenerator.run(generator, "worldChunkManager");
        final Object genLayer = refWorldChunkManagerOverworld.getFieldValue(chunkManager, "genLayer");
        final Object areaLazy = refGenLayer.getFieldValue(genLayer, "areaLazy");
        final Object transformer = refAreaLazy.getFieldValue(areaLazy, "transformer");

        final int[] coords = zoomInto(bukkitWorld.getSeed(), x, y, z);

        final int biomeId = (int) refTransformer.run(transformer, "apply", coords[0], coords[2]);

        Object minecraftKey;
        if (refRegistryExt == null) {
            final Object resourceKey = refRegistry.run("id", biomeId);
            minecraftKey = refMinecraftKeyExt.run(resourceKey, "key");
        } else {
            final Object registry = refRegistryExt.getFieldValue("biomeRegistry");
            final Object object = refRegistry.run(registry, "id", biomeId);
            minecraftKey = refRegistry.run(registry, "key", object);
        }

        final String biomeName = (String) refMinecraftKey.run(minecraftKey, "key");

        return Biome.valueOf(biomeName.toUpperCase());
    }

    public long getSeedHash(final long seed) {
        return hashCache.computeIfAbsent(seed, ignore -> Hashing.sha256().hashLong(seed).asLong());
    }

    private ClassLookup get(final String name) {
        return cache.get(name).orElseThrow(() -> new NullPointerException("Some reflections are not available!"));
    }

    /*
     * Maths
     */

    private int[] zoomInto(final long seed, final int x, final int y, final int z) {
        final long hash = getSeedHash(seed);
        final int var6 = x - 2;
        final int var7 = y - 2;
        final int var8 = z - 2;
        final int var9 = var6 >> 2;
        final int var10 = var7 >> 2;
        final int var11 = var8 >> 2;
        final double var12 = (var6 & 3) / 4.0D;
        final double var14 = (var7 & 3) / 4.0D;
        final double var16 = (var8 & 3) / 4.0D;
        final double[] var18 = new double[8];

        int var19;
        int var23;
        int var24;
        for (var19 = 0; var19 < 8; ++var19) {
            final boolean var20 = (var19 & 4) == 0;
            final boolean var21 = (var19 & 2) == 0;
            final boolean var22 = (var19 & 1) == 0;
            var23 = var20 ? var9 : var9 + 1;
            var24 = var21 ? var10 : var10 + 1;
            final int var25 = var22 ? var11 : var11 + 1;
            final double var26 = var20 ? var12 : var12 - 1.0D;
            final double var28 = var21 ? var14 : var14 - 1.0D;
            final double var30 = var22 ? var16 : var16 - 1.0D;
            var18[var19] = change(hash, var23, var24, var25, var26, var28, var30);
        }

        var19 = 0;
        double var20 = var18[0];

        int var22;
        for (var22 = 1; var22 < 8; ++var22) {
            if (var20 > var18[var22]) {
                var19 = var22;
                var20 = var18[var22];
            }
        }

        var22 = (var19 & 4) == 0 ? var9 : var9 + 1;
        var23 = (var19 & 2) == 0 ? var10 : var10 + 1;
        var24 = (var19 & 1) == 0 ? var11 : var11 + 1;
        return new int[] {
            var22,
            var23,
            var24
        };
    }

    private double change(final long var0, final int var2, final int var3, final int var4, final double var5, final double var7,
        final double var9) {
        long var11 = linearCongruential(var0, var2);
        var11 = linearCongruential(var11, var3);
        var11 = linearCongruential(var11, var4);
        var11 = linearCongruential(var11, var2);
        var11 = linearCongruential(var11, var3);
        var11 = linearCongruential(var11, var4);
        final double var13 = floor(var11);
        var11 = linearCongruential(var11, var0);
        final double var15 = floor(var11);
        var11 = linearCongruential(var11, var0);
        final double var17 = floor(var11);
        return multiply(var9 + var17) + multiply(var7 + var15) + multiply(var5 + var13);
    }

    private double floor(final long var0) {
        final double var2 = (int) Math.floorMod(var0 >> 24, 1024L) / 1024.0D;
        return (var2 - 0.5D) * 0.9D;
    }

    private double multiply(final double var0) {
        return var0 * var0;
    }

    private long linearCongruential(long var0, final long var2) {
        var0 *= var0 * 6364136223846793005L + 1442695040888963407L;
        var0 += var2;
        return var0;
    }

}
