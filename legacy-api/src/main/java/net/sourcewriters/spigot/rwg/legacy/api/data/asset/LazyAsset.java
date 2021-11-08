package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

import net.sourcewriters.spigot.rwg.legacy.api.util.java.tick.ITickReceiver;

public final class LazyAsset<E> implements IAsset<E>, ITickReceiver {

    private final IAssetLoader<E> loader;
    private final File file;
    private final IKey key;

    private AssetUnavailableException error = null;
    private boolean available = false;
    private E asset;

    private int time;
    private int remaining = 0;

    private final ILogger logger;

    public LazyAsset(ILogger logger, IKey key, File file, IAssetLoader<E> loader) {
        this.key = key;
        this.file = file;
        this.loader = loader;
        this.logger = logger;
    }

    @Override
    public IKey getKey() {
        return key;
    }

    void setCacheTime(int time) {
        this.time = time;
        if (remaining > time) {
            remaining = time;
        }
    }

    @Override
    public E get() throws AssetUnavailableException {
        if (error != null) {
            throw error;
        }
        if (available) {
            remaining = time;
            return asset;
        }
        try {
            asset = loader.load(file);
        } catch (IOException exp) {
            throw this.error = new AssetUnavailableException("Failed to load asset '" + key.asString() + "'!", exp);
        }
        remaining = time;
        return asset;
    }

    // This method doesn't load the asset therefore it doesn't reset the cache timer
    @Override
    public E getIfAvailable() {
        return available ? asset : null;
    }

    @Override
    public boolean isFailed() {
        return error != null;
    }

    @Override
    public AssetUnavailableException getFailCause() {
        return error;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public boolean hasFile() {
        return file != null;
    }

    @Override
    public void unload() throws IOException {
        if (!available) {
            return;
        }
        if (asset instanceof Closeable) {
            ((Closeable) asset).close();
        }
        asset = null;
        available = false;
    }

    @Override
    public boolean isUnloadable() {
        return true;
    }

    @Override
    public void onTick(long deltaTime) {
        if (!available) {
            return;
        }
        if (remaining-- == 0) {
            try {
                unload();
            } catch (IOException exp) {
                if (logger != null) {
                    logger.log(LogTypeId.WARNING, "Failed to unload lazy asset '" + key.asString() + "'!");
                    logger.log(LogTypeId.WARNING, exp);
                }
            }
            available = false;
        }
    }

    @Override
    public void close() throws IOException {
        unload();
    }

}
