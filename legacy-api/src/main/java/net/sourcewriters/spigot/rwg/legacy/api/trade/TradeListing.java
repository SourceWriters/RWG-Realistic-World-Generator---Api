package net.sourcewriters.spigot.rwg.legacy.api.trade;

import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

@Unsafe(status = UnsafeStatus.WORK_IN_PROGRESS)
public abstract class TradeListing {
    
    public static final int NMS_DEFAULT_SUPPLY = 12;
    public static final int NMS_COMMON_ITEMS_SUPPLY = 16;
    public static final int NMS_UNCOMMON_ITEMS_SUPPLY = 3;

    public static final int NMS_XP_LEVEL_1_SELL = 1;
    public static final int NMS_XP_LEVEL_1_BUY = 2;
    public static final int NMS_XP_LEVEL_2_SELL = 5;
    public static final int NMS_XP_LEVEL_2_BUY = 10;
    public static final int NMS_XP_LEVEL_3_SELL = 10;
    public static final int NMS_XP_LEVEL_3_BUY = 20;
    public static final int NMS_XP_LEVEL_4_SELL = 15;
    public static final int NMS_XP_LEVEL_4_BUY = 30;
    public static final int NMS_XP_LEVEL_5_TRADE = 30;

    public static final float NMS_LOW_TIER_PRICE_MULTIPLIER = 0.05F;
    public static final float NMS_HIGH_TIER_PRICE_MULTIPLIER = 0.2F;
    
    public static final class Offer {
        
        private ItemStack ingredientA, ingredientB;
        private ItemStack result;
        
        private int maxUses;
        private int uses;
        
        private float priceMultiplier;
        
        private boolean expReward;
        
        private int villagerXp;
        
        public ItemStack getIngredientA() {
            return ingredientA;
        }
        
        public ItemStack getIngredientB() {
            return ingredientB;
        }
        
        public boolean hasExpReward() {
            return expReward;
        }
        
        public int getMaxUses() {
            return maxUses;
        }
        
        public float getPriceMultiplier() {
            return priceMultiplier;
        }
        
        public ItemStack getResult() {
            return result;
        }
        
        public int getUses() {
            return uses;
        }
        
        public int getVillagerXp() {
            return villagerXp;
        }
        
        public void setIngredientA(ItemStack ingredientA) {
            this.ingredientA = ingredientA;
        }
        
        public void setIngredientB(ItemStack ingredientB) {
            this.ingredientB = ingredientB;
        }
        
        public void setExpReward(boolean expReward) {
            this.expReward = expReward;
        }
        
        public void setMaxUses(int maxUses) {
            if (maxUses < 0) {
                throw new IllegalArgumentException("maxUses can't be lower than 0");
            }
            if (maxUses < uses) {
                uses = maxUses;
            }
            this.maxUses = maxUses;
        }
        
        public void setPriceMultiplier(float priceMultiplier) {
            this.priceMultiplier = priceMultiplier;
        }
        
        public void setResult(ItemStack result) {
            this.result = result;
        }
        
        public void setUses(int uses) {
            if (uses < 0) {
                throw new IllegalArgumentException("uses can't be lower than 0");
            }
            if (uses > maxUses) {
                throw new IllegalArgumentException("uses can't be higher than maxUses");
            }
            this.uses = uses;
        }
        
        public void setVillagerXp(int villagerXp) {
            if (villagerXp < 0) {
                throw new IllegalArgumentException("villagerXp can't be lower than 0");
            }
            this.villagerXp = villagerXp;
        }
        
        public boolean isValid() {
            return (!isItemNull(ingredientA) || !isItemNull(ingredientB)) && isItemNull(result) && maxUses != 0;
        }
        
        public static boolean isItemNull(ItemStack stack) {
            return stack == null || stack.getType().isAir() || stack.getAmount() < 0
                || stack.getAmount() > stack.getType().getMaxStackSize();
        }
        
    }
    
    private static class SimpleTradeListing extends TradeListing {
        
        private final BiFunction<Entity, Random, Offer> function;

        public SimpleTradeListing(TradeListingProvider provider, String id, BiFunction<Entity, Random, Offer> function) {
            super(provider, id);
            this.function = Objects.requireNonNull(function, "Function can't be null");
        }
        
        @Override
        public final Offer getOffer(Entity entity, Random random) {
            return function.apply(entity, random);
        }
        
    }
    
    public static TradeListing create(TradeListingProvider provider, String id, BiFunction<Entity, Random, Offer> function) {
        return new SimpleTradeListing(provider, id, function);
    }
    
    private final TradeListingProvider provider;
    private final String id;
    
    public TradeListing(final TradeListingProvider provider, final String id) {
        this.provider = Objects.requireNonNull(provider, "TradeListingProvider can't be null");
        this.id = Objects.requireNonNull(id, "String id can't be null!");
    }
    
    public final String getId() {
        return id;
    }
    
    public final TradeListingProvider getProvider() {
        return provider;
    }
    
    public abstract Offer getOffer(Entity entity, Random random);

}
