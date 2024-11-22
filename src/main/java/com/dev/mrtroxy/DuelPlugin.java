package com.dev.mrtroxy;

import com.dev.mrtroxy.commands.DuelCommand;
import com.dev.mrtroxy.listeners.DuelListener;
import com.dev.mrtroxy.managers.ArenaManager;
import com.dev.mrtroxy.managers.DuelManager;
import com.dev.mrtroxy.managers.StatsManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the DuelPlugin.
 */
public class DuelPlugin extends JavaPlugin {

    private ArenaManager arenaManager;
    private DuelManager duelManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        // Load the default configuration
        saveDefaultConfig();

        // Initialize managers
        arenaManager = new ArenaManager(this);
        duelManager = new DuelManager(this);
        statsManager = new StatsManager(this);

        // Register the /duel command
        getCommand("duel").setExecutor(new DuelCommand(this));
        getCommand("duel").setTabCompleter(new DuelCommand(this));

        // Register event listeners
        getServer().getPluginManager().registerEvents(new DuelListener(this), this);

        getLogger().info("DuelPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        // Save data when the plugin is disabled
        arenaManager.saveArenas();
        statsManager.saveStats();

        getLogger().info("DuelPlugin has been disabled.");
    }

    // Getters for managers
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
