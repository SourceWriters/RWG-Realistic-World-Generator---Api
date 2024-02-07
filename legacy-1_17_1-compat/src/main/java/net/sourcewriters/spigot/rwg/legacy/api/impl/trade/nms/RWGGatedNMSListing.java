package net.sourcewriters.spigot.rwg.legacy.api.impl.trade.nms;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.sourcewriters.spigot.rwg.legacy.api.generator.forward.ForwardHelper;

public final class RWGGatedNMSListing implements ItemListing {

    private final ItemListing original;
    private final ItemStack treasureMapStack, bedrockStack = new ItemStack(Items.BEDROCK);
    
    public RWGGatedNMSListing(ItemListing original) {
        this.original = original;
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(Material.FILLED_MAP);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Treasure Map");
        meta.setLore(Arrays.asList("", ChatColor.RED + "Currently not supported :c"));
        itemStack.setItemMeta(meta);
        this.treasureMapStack = CraftItemStack.asNMSCopy(itemStack);
    }
    
    public ItemListing getOriginal() {
        return original;
    }

    @Override
    public MerchantOffer getOffer(Entity entity, Random random) {
        if (ForwardHelper.isForward(entity.level.getWorld())) {
            return new MerchantOffer(bedrockStack.copy(), ItemStack.EMPTY, treasureMapStack.copy(), 0, 0, 0, 0f, 0);
        }
        return original.getOffer(entity, random);
    }

}
