package net.sourcewriters.spigot.rwg.legacy.api.impl.version.data;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.VersionBackwardDataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public final class BackwardFixer1_13 extends VersionBackwardDataFixer {

    public BackwardFixer1_13(Plugin plugin) {
        super(plugin, "minecraft", MinecraftVersion.of(1, 13, 2));
    }

    @Override
    protected void apply(BlockStateEditor editor) {
        String id = editor.getId();
        if (id.endsWith("_wall_sign")) {
            editor.rename("wall_sign");
            return;
        }
        if (id.endsWith("_sign")) {
            editor.rename("sign");
            return;
        }
    }

}
