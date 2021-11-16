package net.sourcewriters.spigot.rwg.legacy.api.data.fix;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockData;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public interface IDataFixHandler {

    boolean register(@NonNull DataFixer fixer);

    boolean register(@NonNull VersionDataFixer fixer);

    boolean unregister(long id);

    boolean has(long id);

    DataFixer get(long id);

    default String apply(@NonNull final String data) {
        final BlockStateEditor editor = BlockStateEditor.of(data);
        apply(editor);
        return editor.asBlockData();
    }

    default String apply(@NonNull final IBlockData data) {
        final BlockStateEditor editor = BlockStateEditor.of(data);
        apply(editor);
        return editor.asBlockData();
    }

    void apply(@NonNull BlockStateEditor editor);

}
