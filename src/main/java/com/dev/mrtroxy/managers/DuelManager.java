package com.dev.mrtroxy.managers;

import java.util.HashMap;
import java.util.UUID;

import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.utils.DuelRequest;
import com.dev.mrtroxy.models.Arena;
import com.dev.mrtroxy.models.Duel;
import org.bukkit.entity.Player;

/**
 * Manages duel requests and active duels.
 */
public class DuelManager {

    private DuelPlugin plugin;
    private HashMap<UUID, DuelRequest> duelRequests;
    private HashMap<UUID, Duel> activeDuels;

    public DuelManager(DuelPlugin plugin) {
        this.plugin = plugin;
        duelRequests = new HashMap<>();
        activeDuels = new HashMap<>();
    }

    /**
     * Sends a duel request to another player.
     */
    public void sendDuelRequest(Player sender, Player target, String arenaName) {
        if (!plugin.getArenaManager().arenaExists(arenaName)) {
            sender.sendMessage("§cArena does not exist.");
            return;
        }

        DuelRequest request = new DuelRequest(sender.getUniqueId(), target.getUniqueId(), arenaName);
        duelRequests.put(target.getUniqueId(), request);
        sender.sendMessage("§aDuel request sent to " + target.getName());
        target.sendMessage("§e" + sender.getName() + " has challenged you to a duel in arena §b" + arenaName);
        target.sendMessage("§eType §a/duel accept " + sender.getName() + " §eto accept.");
    }

    /**
     * Accepts a duel request from another player.
     */
    public void acceptDuelRequest(Player player, String senderName) {
        Player sender = plugin.getServer().getPlayer(senderName);
        if (sender == null) {
            player.sendMessage("§cPlayer not found.");
            return;
        }

        DuelRequest request = duelRequests.get(player.getUniqueId());
        if (request == null || !request.getSender().equals(sender.getUniqueId())) {
            player.sendMessage("§cNo duel request from that player.");
            return;
        }

        // Start duel
        Arena arena = plugin.getArenaManager().getArena(request.getArenaName());
        if (arena.getSpawnPoint1() == null || arena.getSpawnPoint2() == null) {
            player.sendMessage("§cArena spawn points are not properly set.");
            return;
        }

        Duel duel = new Duel(sender, player, arena, plugin);
        activeDuels.put(sender.getUniqueId(), duel);
        activeDuels.put(player.getUniqueId(), duel);
        duel.start();

        duelRequests.remove(player.getUniqueId());
    }

    /**
     * Declines a duel request from another player.
     */
    public void declineDuelRequest(Player player, String senderName) {
        Player sender = plugin.getServer().getPlayer(senderName);
        if (sender == null) {
            player.sendMessage("§cPlayer not found.");
            return;
        }

        DuelRequest request = duelRequests.get(player.getUniqueId());
        if (request == null || !request.getSender().equals(sender.getUniqueId())) {
            player.sendMessage("§cNo duel request from that player.");
            return;
        }

        duelRequests.remove(player.getUniqueId());
        player.sendMessage("§eYou have declined the duel request from " + sender.getName());
        sender.sendMessage("§c" + player.getName() + " has declined your duel request.");
    }

    /**
     * Checks if a player is currently in a duel.
     */
    public boolean isInDuel(Player player) {
        return activeDuels.containsKey(player.getUniqueId());
    }

    /**
     * Retrieves the duel a player is participating in.
     */
    public Duel getDuel(Player player) {
        return activeDuels.get(player.getUniqueId());
    }

    /**
     * Ends a duel and handles the outcome.
     */
    public void endDuel(Duel duel, Player winner, Player loser) {
        // Remove players from active duels
        activeDuels.remove(duel.getPlayer1().getUniqueId());
        activeDuels.remove(duel.getPlayer2().getUniqueId());

        // Update stats
        plugin.getStatsManager().recordWin(winner);
        plugin.getStatsManager().recordLoss(loser);

        // Reward winner
        int coinsPerWin = plugin.getConfig().getInt("coins-per-win", 10);
        plugin.getStatsManager().addCoins(winner, coinsPerWin);

        // Send messages
        winner.sendMessage("§aYou won the duel and earned " + coinsPerWin + " coins!");
        loser.sendMessage("§cYou lost the duel.");

        // Additional cleanup if needed
    }
}
