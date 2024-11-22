package com.dev.mrtroxy.managers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.UUID;

import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.utils.PlayerStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.bukkit.entity.Player;

/**
 * Manages player statistics such as wins, losses, and coins.
 */
public class StatsManager {

    private DuelPlugin plugin;
    private HashMap<UUID, PlayerStats> playerStats;

    public StatsManager(DuelPlugin plugin) {
        this.plugin = plugin;
        playerStats = new HashMap<>();
        loadStats();
    }

    /**
     * Loads player statistics from stats.json.
     */
    public void loadStats() {
        File file = new File(plugin.getDataFolder(), "stats.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                playerStats = gson.fromJson(reader, new TypeToken<HashMap<UUID, PlayerStats>>() {}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves player statistics to stats.json.
     */
    public void saveStats() {
        File file = new File(plugin.getDataFolder(), "stats.json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(playerStats, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the stats of a player.
     */
    public PlayerStats getStats(Player player) {
        playerStats.putIfAbsent(player.getUniqueId(), new PlayerStats());
        return playerStats.get(player.getUniqueId());
    }

    /**
     * Records a win for a player.
     */
    public void recordWin(Player player) {
        PlayerStats stats = getStats(player);
        stats.incrementWins();
    }

    /**
     * Records a loss for a player.
     */
    public void recordLoss(Player player) {
        PlayerStats stats = getStats(player);
        stats.incrementLosses();
    }

    /**
     * Adds coins to a player's account.
     */
    public void addCoins(Player player, int amount) {
        PlayerStats stats = getStats(player);
        stats.addCoins(amount);
    }
}
