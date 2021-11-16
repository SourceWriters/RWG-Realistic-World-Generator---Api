package net.sourcewriters.spigot.rwg.legacy.api.util.java.tick;

@FunctionalInterface
public interface ITickReceiver {

    void onTick(long deltaTime);

}
