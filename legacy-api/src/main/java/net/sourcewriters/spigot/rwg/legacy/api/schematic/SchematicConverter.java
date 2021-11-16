package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

public abstract class SchematicConverter {

    private final long id;

    private final ILogger logger;

    private final Plugin plugin;
    private final ArrayList<String> extensions = new ArrayList<>();

    public SchematicConverter(final ILogger logger, final Plugin plugin, final String... extensions) {
        this.logger = logger;
        this.plugin = plugin;
        Collections.addAll(this.extensions, extensions);
        this.id = plugin.getName().hashCode() + extensions.hashCode() * 32;
    }

    public final Plugin getPlugin() {
        return plugin;
    }

    public final long getId() {
        return id;
    }

    public final boolean canConvert(final String extension) {
        return extensions.contains(extension);
    }

    public final String[] getExtensions() {
        return extensions.toArray(String[]::new);
    }

    protected abstract File internalConvert(File file) throws Exception;

    public final File convert(final File file, final String extension) {
        if (!canConvert(extension)) {
            return null;
        }
        try {
            return internalConvert(file);
        } catch (final Exception exp) {
            if (logger != null && logger.getState().extendedInfo()) {
                logger.log(LogTypeId.DEBUG, exp);
            }
            return null;
        }
    }

}
