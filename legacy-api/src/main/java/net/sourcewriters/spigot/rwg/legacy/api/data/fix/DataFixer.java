package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import java.util.Objects;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public abstract class DataFixer {

    private final long id;
    private final Plugin plugin;
    private final String namespace;

    public DataFixer(@NonNull final Plugin plugin, @NonNull final String namespace) {
        this(0, plugin, namespace);
    }

    public DataFixer(final long specialHash, @NonNull final Plugin plugin, @NonNull final String namespace) {
        Objects.requireNonNull(plugin, "Plugin can't be null!");
        Objects.requireNonNull(plugin, "String namespace can't be null!");
        this.id = specialHash + namespace.hashCode() * 32 + plugin.getName().hashCode() * 1024;
        this.plugin = plugin;
        this.namespace = namespace;
    }

    @NonNull
    public final Plugin getPlugin() {
        return plugin;
    }

    @NonNull
    public final String getNamespace() {
        return namespace;
    }

    public final long getId() {
        return id;
    }

    protected abstract void apply(@NonNull BlockStateEditor editor);

    public final void tryApply(@NonNull final BlockStateEditor editor) {
        if (!Objects.requireNonNull(editor, "BlockStateEditor can't be null!").getNamespace().equals(namespace)) {
            return;
        }
        apply(editor);
    }

}
