package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface IAssetLoader<E> {

    E load(File file) throws IOException;
    
}
