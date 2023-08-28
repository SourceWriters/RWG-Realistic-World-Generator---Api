package net.sourcewriters.spigot.rwg.legacy.api.impl.trade;

import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.logging.LogTypeId;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.sourcewriters.spigot.rwg.legacy.api.impl.trade.nms.RWGGatedNMSListing;
import net.sourcewriters.spigot.rwg.legacy.api.trade.ITradeListingManager;
import net.sourcewriters.spigot.rwg.legacy.api.trade.TradeListing;
import net.sourcewriters.spigot.rwg.legacy.api.trade.TradeListingProvider;
import net.sourcewriters.spigot.rwg.legacy.api.util.java.reflect.JavaAccess;

// TODO: Work on this
public class TradeListingManagerImpl implements ITradeListingManager {
    
    private final ILogger logger;

    public TradeListingManagerImpl(ILogger logger) {
        this.logger = logger;
        logger.log(LogTypeId.INFO, "Important: This version of the RWG Api does not fully support the custom 'ITradeListingManager' yet!");
        inject();
    }

    @Override
    public void registerListing(TradeListing listing) {
    }

    @Override
    public void unregisterListing(TradeListing listing) {
    }

    @Override
    public void unregisterListing(String namespacedKey) {
    }

    @Override
    public void unregister(TradeListingProvider provider) {
    }

    @Override
    public void unregister(Plugin plugin) {
    }
    
    public void inject() {
        VillagerProfession[] professions = VillagerTrades.TRADES.keySet().toArray(VillagerProfession[]::new);
        for (VillagerProfession profession : professions) {
            Int2ObjectMap<ItemListing[]> map = VillagerTrades.TRADES.get(profession);
            int[] levels = map.keySet().toIntArray();
            for (int level : levels) {
                ItemListing[] listings = map.get(level);
                for (int i = 0; i < listings.length; i++) {
                    ItemListing listing = listings[i];
                    if (JavaAccess.getDeclaredField(listing.getClass(), MapDecoration.Type.class) == null) {
                        continue;
                    }
                    listings[i] = new RWGGatedNMSListing(listing);
                }
            }
        }
    }

    public void uninject() {
        VillagerProfession[] professions = VillagerTrades.TRADES.keySet().toArray(VillagerProfession[]::new);
        for (VillagerProfession profession : professions) {
            Int2ObjectMap<ItemListing[]> map = VillagerTrades.TRADES.get(profession);
            int[] levels = map.keySet().toIntArray();
            for (int level : levels) {
                ItemListing[] listings = map.get(level);
                for (int i = 0; i < listings.length; i++) {
                    if (listings[i] instanceof RWGGatedNMSListing) {
                        listings[i] = ((RWGGatedNMSListing) listings[i]).getOriginal();
                    }
                }
            }
        }
    }

}
