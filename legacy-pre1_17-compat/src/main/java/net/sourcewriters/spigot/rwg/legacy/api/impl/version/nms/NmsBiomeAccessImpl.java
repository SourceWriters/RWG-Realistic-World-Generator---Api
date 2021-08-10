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

    public NmsBiomeAccessImpl(ClassLookupProvider provider) {
        this.cache = provider.getCache();
    }

    @Override
    public Biome getBiomeAt(World bukkitWorld, int x, int y, int z) {
        Optional<ClassLookup> option;
        ClassLookup refCbWorld = get("cb_world");
        ClassLookup refWorldServer = get("nms_world_server");
        ClassLookup refChunkProviderServer = get("nms_chunk_provider_server");
        ClassLookup refChunkGenerator = get("nms_chunk_generator");
        ClassLookup refWorldChunkManagerOverworld = get("nms_world_chunk_manager_overworld");
        ClassLookup refGenLayer = get("nms_gen_layer");
        ClassLookup refAreaLazy = get("nms_area_lazy");
        ClassLookup refTransformer = get("nms_area_transformer_8");
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
        ClassLookup refMinecraftKey = get("nms_minecraft_key");

        Object worldServer = refCbWorld.run(bukkitWorld, "handle");
        Object chunkProviderServer = refWorldServer.run(worldServer, "chunkProvider");
        Object generator = refChunkProviderServer.run(chunkProviderServer, "generator");
        Object chunkManager = refChunkGenerator.run(generator, "worldChunkManager");
        Object genLayer = refWorldChunkManagerOverworld.getFieldValue(chunkManager, "genLayer");
        Object areaLazy = refGenLayer.getFieldValue(genLayer, "areaLazy");
        Object transformer = refAreaLazy.getFieldValue(areaLazy, "transformer");

        int[] coords = zoomInto(bukkitWorld.getSeed(), x, y, z);

        int biomeId = (int) refTransformer.run(transformer, "apply", coords[0], coords[2]);

        Object minecraftKey;
        if (refRegistryExt == null) {
            Object resourceKey = refRegistry.run("id", biomeId);
            minecraftKey = refMinecraftKeyExt.run(resourceKey, "key");
        } else {
            Object registry = refRegistryExt.getFieldValue("biomeRegistry");
            Object object = refRegistry.run(registry, "id", biomeId);
            minecraftKey = refRegistry.run(registry, "key", object);
        }

        String biomeName = (String) refMinecraftKey.run(minecraftKey, "key");

        return Biome.valueOf(biomeName.toUpperCase());
    }

    public long getSeedHash(long seed) {
        return hashCache.computeIfAbsent(seed, ignore -> Hashing.sha256().hashLong(seed).asLong());
    }

    private ClassLookup get(String name) {
        return cache.get(name).orElseThrow(() -> new NullPointerException("Some reflections are not available!"));
    }

    /*
     * Maths
     */

    private int[] zoomInto(long seed, int x, int y, int z) {
        long hash = getSeedHash(seed);
        int var6 = x - 2;
        int var7 = y - 2;
        int var8 = z - 2;
        int var9 = var6 >> 2;
        int var10 = var7 >> 2;
        int var11 = var8 >> 2;
        double var12 = (double) (var6 & 3) / 4.0D;
        double var14 = (double) (var7 & 3) / 4.0D;
        double var16 = (double) (var8 & 3) / 4.0D;
        double[] var18 = new double[8];

        int var19;
        int var23;
        int var24;
        for (var19 = 0; var19 < 8; ++var19) {
            boolean var20 = (var19 & 4) == 0;
            boolean var21 = (var19 & 2) == 0;
            boolean var22 = (var19 & 1) == 0;
            var23 = var20 ? var9 : var9 + 1;
            var24 = var21 ? var10 : var10 + 1;
            int var25 = var22 ? var11 : var11 + 1;
            double var26 = var20 ? var12 : var12 - 1.0D;
            double var28 = var21 ? var14 : var14 - 1.0D;
            double var30 = var22 ? var16 : var16 - 1.0D;
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

    private double change(long var0, int var2, int var3, int var4, double var5, double var7, double var9) {
        long var11 = linearCongruential(var0, (long) var2);
        var11 = linearCongruential(var11, (long) var3);
        var11 = linearCongruential(var11, (long) var4);
        var11 = linearCongruential(var11, (long) var2);
        var11 = linearCongruential(var11, (long) var3);
        var11 = linearCongruential(var11, (long) var4);
        double var13 = floor(var11);
        var11 = linearCongruential(var11, var0);
        double var15 = floor(var11);
        var11 = linearCongruential(var11, var0);
        double var17 = floor(var11);
        return multiply(var9 + var17) + multiply(var7 + var15) + multiply(var5 + var13);
    }

    private double floor(long var0) {
        double var2 = (double) ((int) Math.floorMod(var0 >> 24, 1024L)) / 1024.0D;
        return (var2 - 0.5D) * 0.9D;
    }

    private double multiply(double var0) {
        return var0 * var0;
    }

    private long linearCongruential(long var0, long var2) {
        var0 *= var0 * 6364136223846793005L + 1442695040888963407L;
        var0 += var2;
        return var0;
    }

}
