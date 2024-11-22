package com.dev.mrtroxy.listeners;

import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.models.Duel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

/**
 * Listens for events related to duels.
 */
public class DuelListener implements Listener {

    private DuelPlugin plugin;

    public DuelListener(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles player deaths to determine the outcome of a duel.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        if (plugin.getDuelManager().isInDuel(deceased)) {
            Duel duel = plugin.getDuelManager().getDuel(deceased);
            Player winner = duel.getPlayer1().equals(deceased) ? duel.getPlayer2() : duel.getPlayer1();
            duel.end(winner, deceased);
        }
    }
}
