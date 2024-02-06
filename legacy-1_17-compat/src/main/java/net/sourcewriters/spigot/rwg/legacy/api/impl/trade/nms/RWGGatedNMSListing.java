package net.sourcewriters.spigot.rwg.legacy.api.impl.trade.nms;

import java.util.Random;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
            ItemStack stack = new ItemStack(Items.FILLED_MAP);
            stack.setCount(1);
            TextComponent component = new TextComponent("Treasure map");
            component.setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE));
            stack.setHoverName(component);
            CompoundTag tag = stack.getOrCreateTagElement("display");
            ListTag listTag = tag.getList("Lore", 8);
            listTag.add(StringTag.valueOf(TextComponent.EMPTY.getString()));
            component = new TextComponent("Currently not supported :c");
            component.setStyle(Style.EMPTY.withColor(ChatFormatting.RED).withItalic(true));
            listTag.add(StringTag.valueOf(component.getString()));
            tag.put("Lore", listTag);
            stack.setTag(tag);
            return new MerchantOffer(new ItemStack(Items.BEDROCK), ItemStack.EMPTY, stack, 0, 0, 0, 0f, 0);
        }
        return original.getOffer(entity, random);
    }

}
