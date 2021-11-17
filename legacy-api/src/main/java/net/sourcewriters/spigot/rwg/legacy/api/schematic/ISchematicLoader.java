package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import java.io.File;
import java.util.function.Supplier;

import net.sourcewriters.spigot.rwg.legacy.api.data.asset.IAsset;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface ISchematicLoader {

    /**
     * Copies a file into the RWG schematic folder and loads it afterwards. If
     * necessary it also converts the schematic into a valid schematic format if
     * possible.
     * 
     * @param  file                     the schematic that should be loaded
     * 
     * @return                          the loaded schematic
     * 
     * @throws IllegalArgumentException if the file is not loadable nor convertable
     * @throws Exception                if something goes wrong while loading or
     *                                      converting the file to a valid schematic
     *                                      format
     */
    @NonNull
    IAsset<ISchematic> load(@NonNull File file) throws Exception;

    boolean register(@NonNull SchematicConverter converter);

    default boolean register(final boolean expression, @NonNull final Supplier<SchematicConverter> converter) {
        if (!expression) {
            return false;
        }
        return register(converter.get());
    }

    boolean unregister(long id);

    boolean has(long id);

    SchematicConverter get(long id);

}
