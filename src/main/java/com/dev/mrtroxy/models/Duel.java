package com.dev.mrtroxy.models;

import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.models.Arena;
import org.bukkit.entity.Player;

/**
 * Represents an active duel between two players.
 */
public class Duel {

    private Player player1;
    private Player player2;
    private Arena arena;
    private DuelPlugin plugin;

    public Duel(Player player1, Player player2, Arena arena, DuelPlugin plugin) {
        this.player1 = player1;
        this.player2 = player2;
        this.arena = arena;
        this.plugin = plugin;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Arena getArena() {
        return arena;
    }

    /**
     * Starts the duel by teleporting players to the arena.
     */
    public void start() {
        // Teleport players to arena spawn points
        player1.teleport(arena.getSpawnPoint1());
        player2.teleport(arena.getSpawnPoint2());

        // Send messages
        player1.sendMessage("§aDuel started against " + player2.getName());
        player2.sendMessage("§aDuel started against " + player1.getName());
    }

    /**
     * Ends the duel and notifies the DuelManager.
     */
    public void end(Player winner, Player loser) {
        plugin.getDuelManager().endDuel(this, winner, loser);
    }
}
