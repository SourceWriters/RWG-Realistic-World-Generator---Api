package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CompatibilityAddonEnableEvent extends Event {

    public static final HandlerList HANDLER_LIST = new HandlerList();

    private final CompatibilityAddon addon;

    public CompatibilityAddonEnableEvent(final CompatibilityAddon addon) {
        this.addon = addon;
    }

    public CompatibilityAddon getAddon() {
        return addon;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
