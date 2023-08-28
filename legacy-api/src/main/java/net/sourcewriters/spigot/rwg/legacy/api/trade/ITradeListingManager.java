package net.sourcewriters.spigot.rwg.legacy.api.trade;

import org.bukkit.plugin.Plugin;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

@Unsafe(status = UnsafeStatus.WORK_IN_PROGRESS)
public interface ITradeListingManager {
    
    void registerListing(TradeListing listing);
    
    void unregisterListing(TradeListing listing);
    
    void unregisterListing(String namespacedKey);
    
    void unregister(TradeListingProvider provider);
    
    void unregister(Plugin plugin);
    
}
