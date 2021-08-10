package net.sourcewriters.spigot.rwg.legacy.api.impl.data;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.VersionForwardDataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public final class ForwardFixer1_16 extends VersionForwardDataFixer {

    public ForwardFixer1_16(Plugin plugin) {
        super(plugin, "minecraft", MinecraftVersion.of(1, 16));
    }

    @Override
    protected void apply(BlockStateEditor editor) {
        String id = editor.getId();
        if (id.endsWith("_wall")) {
            editor.map(value -> Boolean.parseBoolean(value) ? "tall" : "none", "west", "east", "north", "south");
            return;
        }
    }

}
