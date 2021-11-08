package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.registry.ConcurrentRegistry;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.tick.Ticker;

public final class AssetManager implements Closeable {

    public static final int MAX_CACHE_TIME = 3600;
    public static final int MIN_CACHE_TIME = 1;

    private static final AtomicInteger ID = new AtomicInteger(0);

    private final ConcurrentRegistry<IAsset<?>> registry = new ConcurrentRegistry<>();
    private final Ticker ticker = new Ticker("AssetCache-" + ID.getAndIncrement());

    private final ILogger logger;
    private final int cacheTime;

    public AssetManager(ILogger logger, int cacheTime) {
        this.cacheTime = Math.min(Math.max(cacheTime, MIN_CACHE_TIME), MAX_CACHE_TIME);
        this.logger = logger;
        ticker.pause();
    }

    public IAsset<?> get(IKey key) {
        return registry.get(key);
    }

    public boolean has(IKey key) {
        return registry.has(key);
    }

    public IAsset<?> unregister(IKey key) {
        IAsset<?> asset = registry.get(key);
        if (asset == null) {
            return asset;
        }
        registry.unregister(key);
        try {
            asset.close();
        } catch (IOException exp) {
            if (logger != null) {
                logger.log(LogTypeId.WARNING, "Failed to close asset '" + key.asString() + "' while unregistering!");
                logger.log(LogTypeId.WARNING, exp);
            }
        }
        if (!(asset instanceof LazyAsset)) {
            return asset;
        }
        ticker.remove((LazyAsset<?>) asset);
        if (ticker.getReceiverCount() == 0) {
            ticker.pause();
        }
        return asset;
    }

    public <E> IAsset<E> createLazy(IKey key, BiFunction<ILogger, IKey, LazyAsset<E>> function) {
        if (registry.has(key)) {
            return null;
        }
        LazyAsset<E> asset = function.apply(logger, key);
        if (asset != null) {
            registry.register(asset);
            ticker.add(asset);
            if (ticker.isPaused()) {
                ticker.start();
            }
        }
        return asset;
    }

    public <E> IAsset<E> createPersistent(IKey key, BiFunction<ILogger, IKey, PersistentAsset<E>> function) {
        if (registry.has(key)) {
            return null;
        }
        IAsset<E> asset = function.apply(logger, key);
        if (asset != null) {
            registry.register(asset);
        }
        return asset;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public ILogger getLogger() {
        return logger;
    }

    @Override
    public void close() throws IOException {
        ticker.stop();
        List<IKey> keys = registry.getKeys();
        for (IKey key : keys) {
            try {
                registry.get(key).close();
            } catch (IOException exp) {
                if (logger != null) {
                    logger.log("Failed to close asset '" + key.asString() + "'!");
                }
            }
            registry.unregister(key);
        }
    }

}
