package net.sourcewriters.spigot.rwg.legacy.api.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.CallerSensitive;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.rwg.RWGMaterial;

public interface IBlockAccess {

    @NonNull
    IBlockDataLoaderManager getLoaderManager();

    @NonNull
    IBlockDataParserManager getParserManager();

    @NonNull
    IBlockDataPlacerManager getPlacerManager();

    @CallerSensitive
    IBlockData dataOf(@NonNull Block block);
    
    @CallerSensitive
    IBlockData dataOf(@NonNull BlockState blockState);

    @CallerSensitive
    IBlockData dataOf(@NonNull String rawData);

    IBlockData dataOf(@NonNull BlockData data);

    IBlockData dataOf(@NonNull Material material);

    IBlockData dataOf(@NonNull RWGMaterial material);

    @CallerSensitive
    IBlockData dataOf(@NonNull NbtCompound compound);

}
