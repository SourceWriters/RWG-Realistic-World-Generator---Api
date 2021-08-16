package net.sourcewriters.spigot.rwg.legacy.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.spigot.rwg.legacy.api.block.IBlockAccess;
import net.sourcewriters.spigot.rwg.legacy.api.chest.IChestStorage;
import net.sourcewriters.spigot.rwg.legacy.api.compatibility.ICompatibilityManager;
import net.sourcewriters.spigot.rwg.legacy.api.data.fix.IDataFixHandler;
import net.sourcewriters.spigot.rwg.legacy.api.schematic.ISchematicStorage;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.version.IVersionAccess;

public abstract class RealisticWorldGenerator {

    public static RealisticWorldGenerator get() {
        RegisteredServiceProvider<RealisticWorldGenerator> provider = Bukkit.getServicesManager()
            .getRegistration(RealisticWorldGenerator.class);
        return provider != null ? provider.getProvider() : null;
    }

    protected RealisticWorldGenerator(Plugin plugin) {
        Bukkit.getServicesManager().register(RealisticWorldGenerator.class, this, plugin, ServicePriority.Highest);
    }

    @NonNull
    public abstract IBlockAccess getBlockAccess();

    @NonNull
    public abstract IChestStorage getChestStorage();

    @NonNull
    public abstract IVersionAccess getVersionAccess();

    @NonNull
    public abstract IDataFixHandler getDataFixHandler();

    @NonNull
    public abstract ISchematicStorage getSchematicStorage();

    @NonNull
    public abstract ICompatibilityManager getCompatibilityManager();
    
    @NonNull
    public abstract ILogger getLogger();

}
