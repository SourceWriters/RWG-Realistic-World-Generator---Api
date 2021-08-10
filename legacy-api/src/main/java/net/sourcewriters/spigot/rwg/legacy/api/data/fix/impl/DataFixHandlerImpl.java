package net.sourcewriters.spigot.rwg.legacy.api.data.fix.impl;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.sourcewriters.spigot.rwg.legacy.api.block.BlockStateEditor;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.DataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.VersionDataFixer;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public class DataFixHandlerImpl implements IDataFixHandler {

    private final ConcurrentHashMap<Long, DataFixer> fixers = new ConcurrentHashMap<>();

    @Override
    public boolean register(@NonNull DataFixer fixer) {
        if (fixer instanceof VersionDataFixer) {
            return register((VersionDataFixer) fixer);
        }
        Objects.requireNonNull(fixer, "DataFixer can't be null!");
        if (has(fixer.getId())) {
            return false;
        }
        fixers.put(fixer.getId(), fixer);
        return true;
    }

    @Override
    public boolean register(@NonNull VersionDataFixer fixer) {
        Objects.requireNonNull(fixer, "VersionDataFixer can't be null!");
        if (has(fixer.getId()) || !fixer.isSupported(Versions.getMinecraft())) {
            return false;
        }
        fixers.put(fixer.getId(), fixer);
        return true;
    }

    @Override
    public boolean unregister(long id) {
        return fixers.remove(id) != null;
    }

    @Override
    public boolean has(long id) {
        return fixers.containsKey(id);
    }

    @Override
    public DataFixer get(long id) {
        return fixers.get(id);
    }

    @Override
    public void apply(BlockStateEditor editor) {
        for (DataFixer fixer : fixers.values()) {
            fixer.tryApply(editor);
        }
    }

}
