package net.sourcewriters.spigot.rwg.legacy.api.impl.trade.nms;

import java.util.Random;

import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.sourcewriters.spigot.rwg.legacy.api.generator.forward.ForwardHelper;
import net.sourcewriters.spigot.rwg.legacy.api.trade.TradeListing;

public final class RWGItemListing implements ItemListing {

    private final TradeListing itemListing;

    public RWGItemListing(final TradeListing itemListing) {
        this.itemListing = itemListing;
    }

    @Override
    public MerchantOffer getOffer(Entity entity, Random random) {
        if (!ForwardHelper.isForward(entity.level.getWorld())) {
            return null;
        }
        TradeListing.Offer offer = itemListing.getOffer(entity.getBukkitEntity(), random);
        if (offer == null || !offer.isValid()) {
            return null;
        }
        org.bukkit.inventory.ItemStack stackA = offer.getIngredientA();
        org.bukkit.inventory.ItemStack stackB = offer.getIngredientB();
        ItemStack nmsStackA = ItemStack.EMPTY;
        ItemStack nmsStackB = ItemStack.EMPTY;
        if (TradeListing.Offer.isItemNull(stackA)) {
            nmsStackA = CraftItemStack.asNMSCopy(stackB);
        } else {
            nmsStackA = CraftItemStack.asNMSCopy(stackA);
            if (TradeListing.Offer.isItemNull(stackB)) {
                nmsStackB = CraftItemStack.asNMSCopy(stackB);
            }
        }
        ItemStack result = CraftItemStack.asNMSCopy(offer.getResult());
        return new MerchantOffer(nmsStackA, nmsStackB, result, offer.getUses(), offer.getMaxUses(), offer.getVillagerXp(),
            offer.getPriceMultiplier(), 0);
    }

}
