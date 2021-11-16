package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

public final class PersistentAsset<E> implements IAsset<E> {

    private final IKey key;
    private final E asset;
    private final File file;

    public PersistentAsset(final ILogger logger, final IKey key, final File file, final IAssetLoader<E> loader) {
        E tmpAsset = null;
        try {
            tmpAsset = loader.load(file);
        } catch (final IOException exp) {
            if (logger != null) {
                logger.log(LogTypeId.WARNING, "Failed to load persistent asset '" + key.asString() + "'!");
                logger.log(LogTypeId.WARNING, exp);
            }
        }
        this.asset = tmpAsset;
        this.file = file;
        this.key = key;
    }

    public PersistentAsset(final IKey key, final File file, final E asset) {
        this.asset = asset;
        this.file = file == null && asset instanceof IFiled ? ((IFiled) asset).getFile() : null;
        this.key = key;
    }

    public PersistentAsset(final IKey key, final E asset) {
        this(key, null, asset);
    }

    @Override
    public IKey getKey() {
        return key;
    }

    @Override
    public E get() throws AssetUnavailableException {
        return asset;
    }

    @Override
    public E getIfAvailable() {
        return asset;
    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public AssetUnavailableException getFailCause() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return true;
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
    public void unload() {
        throw new UnsupportedOperationException("Can't unload a persistent Asset!");
    }

    @Override
    public boolean isUnloadable() {
        return false;
    }

    @Override
    public void close() throws IOException {
        if (asset instanceof Closeable) {
            ((Closeable) asset).close();
        }
    }

}
