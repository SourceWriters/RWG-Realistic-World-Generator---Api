package net.sourcewriters.spigot.rwg.legacy.api.chest;

import java.util.Random;

import org.bukkit.block.Container;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.RandomAdapter;

@Unsafe(status = UnsafeStatus.WORK_IN_PROGRESS, useable = true)
public interface IChestStorage {

    void fillContainer(Container container);

    default void fillContainer(Container container, Random random) {
        fillContainer(container, new RandomAdapter(random));
    }

    void fillContainer(Container container, RandomNumberGenerator random);

}
