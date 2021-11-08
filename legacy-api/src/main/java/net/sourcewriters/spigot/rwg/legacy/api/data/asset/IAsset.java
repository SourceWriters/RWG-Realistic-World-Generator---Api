package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.File;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.utils.key.IKeyed;

public interface IAsset<E> extends IKeyed {

    E get() throws AssetUnavailableException;

    E getIfAvailable();

    boolean isFailed();

    AssetUnavailableException getFailCause();

    boolean isAvailable();

    File getFile();

    boolean hasFile();

    void unload() throws IOException;

    boolean isUnloadable();

    void close() throws IOException;

}
