package net.sourcewriters.spigot.rwg.legacy.api.impl.data;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.VersionBackwardDataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public final class BackwardFixer1_15 extends VersionBackwardDataFixer {

    public BackwardFixer1_15(Plugin plugin) {
        super(plugin, "minecraft", MinecraftVersion.of(1, 15, 2));
    }

    @Override
    protected void apply(BlockStateEditor editor) {
        String id = editor.getId();
        if (id.endsWith("_wall")) {
            editor.map(value -> "" + (!value.equalsIgnoreCase("none")), "west", "east", "north", "south");
            return;
        }
    }

}
