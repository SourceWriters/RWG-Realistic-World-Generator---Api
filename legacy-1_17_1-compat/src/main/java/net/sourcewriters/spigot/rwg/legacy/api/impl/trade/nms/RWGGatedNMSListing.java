package net.sourcewriters.spigot.rwg.legacy.api.impl.trade.nms;

import java.util.Random;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.MerchantOffer;
import net.sourcewriters.spigot.rwg.legacy.api.generator.forward.ForwardHelper;

public final class RWGGatedNMSListing implements ItemListing {

    private final ItemListing original;
    
    public RWGGatedNMSListing(ItemListing original) {
        this.original = original;
    }
    
    public ItemListing getOriginal() {
        return original;
    }

    @Override
    public MerchantOffer getOffer(Entity entity, Random random) {
        if (ForwardHelper.isForward(entity.level.getWorld())) {
            return null;
        }
        return original.getOffer(entity, random);
    }

}
