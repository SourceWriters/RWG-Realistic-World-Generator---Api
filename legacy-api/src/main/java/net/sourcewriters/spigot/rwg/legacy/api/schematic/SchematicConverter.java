package net.sourcewriters.spigot.rwg.legacy.api.schematic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.plugin.Plugin;

public abstract class SchematicConverter {
    
    private final long id;

    private final Plugin plugin;
    private final ArrayList<String> extensions = new ArrayList<>();
    
    public SchematicConverter(Plugin plugin, String... extensions) {
        this.plugin = plugin;
        Collections.addAll(this.extensions, extensions);
        this.id = plugin.getName().hashCode() + (extensions.hashCode() * 32);
    }
    
    public final Plugin getPlugin() {
        return plugin;
    }
    
    public final long getId() {
        return id;
    }
    
    public final boolean canConvert(String extension) {
        return extensions.contains(extension);
    }
    
    public final String[] getExtensions() {
        return extensions.toArray(String[]::new);
    }
    
    protected abstract File internalConvert(File file) throws Exception;

    public final File convert(File file, String extension) {
        if (!canConvert(extension)) {
            return null;
        }
        try {
            return internalConvert(file);
        } catch (Exception exp) {
            return null;
        }
    }

}
