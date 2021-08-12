package net.sourcewriters.spigot.rwg.legacy.api.chest;

import java.util.Random;

import org.bukkit.inventory.Inventory;

import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.RandomAdapter;

@Unsafe(status = UnsafeStatus.WORK_IN_PROGRESS, useable = true)
public interface IChestStorage {

    void fillInventory(String chest, Inventory inventory);

    default void fillInventory(String chest, Inventory inventory, Random random) {
        fillInventory(chest, inventory, new RandomAdapter(random));
    }

    void fillInventory(String chest, Inventory inventory, RandomNumberGenerator random);

}
