package net.sourcewriters.spigot.rwg.legacy.api.impl.version.data;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.VersionForwardDataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.MinecraftVersion;

public final class ForwardFixer1_14 extends VersionForwardDataFixer {

    public ForwardFixer1_14(Plugin plugin) {
        super(plugin, "minecraft", MinecraftVersion.of(1, 14));
    }

    @Override
    protected void apply(BlockStateEditor editor) {
        String id = editor.getId();
        if (id.startsWith("wall_sign")) {
            editor.rename("oak_wall_sign");
            return;
        }
        if (id.startsWith("sign")) {
            editor.rename("oak_sign");
            return;
        }
    }

}
