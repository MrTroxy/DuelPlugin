package com.dev.mrtroxy.commands;

import com.dev.mrtroxy.models.Arena;
import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.utils.PlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the /duel command and its subcommands.
 */
public class DuelCommand implements CommandExecutor, TabCompleter {

    private DuelPlugin plugin;

    public DuelCommand(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("duel.use")) {
            player.sendMessage("§cYou do not have permission to use duel commands.");
            return true;
        }

        if (args.length == 0) {
            // Show help or usage
            player.sendMessage("§e--- Duel Commands ---");
            player.sendMessage("§e/duel [player] [arena] - Send duel request");
            player.sendMessage("§e/duel accept [player] - Accept duel request");
            player.sendMessage("§e/duel decline [player] - Decline duel request");
            if (player.hasPermission("duel.create")) {
                player.sendMessage("§e/duel create [arena] - Create an arena");
            }
            if (player.hasPermission("duel.setspawn1")) {
                player.sendMessage("§e/duel setspawn1 [arena] - Set arena spawn point 1");
            }
            if (player.hasPermission("duel.setspawn2")) {
                player.sendMessage("§e/duel setspawn2 [arena] - Set arena spawn point 2");
            }
            player.sendMessage("§e/duel arenalist - List all available arenas");
            player.sendMessage("§e/duel stats - View your stats");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "accept":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel accept [player]");
                    return true;
                }
                plugin.getDuelManager().acceptDuelRequest(player, args[1]);
                break;
            case "decline":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel decline [player]");
                    return true;
                }
                plugin.getDuelManager().declineDuelRequest(player, args[1]);
                break;
            case "create":
                if (!player.hasPermission("duel.create")) {
                    player.sendMessage("§cYou do not have permission to create arenas.");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel create [arena]");
                    return true;
                }
                String arenaName = args[1];
                if (plugin.getArenaManager().arenaExists(arenaName)) {
                    player.sendMessage("§cAn arena with that name already exists.");
                    return true;
                }
                plugin.getArenaManager().createArena(arenaName);
                player.sendMessage("§aArena " + arenaName + " created. Use /duel setspawn1 or /duel setspawn2 to set spawn points.");
                break;
            case "setspawn1":
                if (!player.hasPermission("duel.setspawn1")) {
                    player.sendMessage("§cYou do not have permission to set spawn point 1.");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel setspawn1 [arena]");
                    return true;
                }
                arenaName = args[1];
                Arena arena1 = plugin.getArenaManager().getArena(arenaName);
                if (arena1 == null) {
                    player.sendMessage("§cArena not found.");
                    return true;
                }
                arena1.setSpawnPoint1(player.getLocation());
                player.sendMessage("§aSpawn point 1 set for arena " + arenaName);
                plugin.getArenaManager().saveArenas();
                break;
            case "setspawn2":
                if (!player.hasPermission("duel.setspawn2")) {
                    player.sendMessage("§cYou do not have permission to set spawn point 2.");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel setspawn2 [arena]");
                    return true;
                }
                arenaName = args[1];
                Arena arena2 = plugin.getArenaManager().getArena(arenaName);
                if (arena2 == null) {
                    player.sendMessage("§cArena not found.");
                    return true;
                }
                arena2.setSpawnPoint2(player.getLocation());
                player.sendMessage("§aSpawn point 2 set for arena " + arenaName);
                plugin.getArenaManager().saveArenas();
                break;
            case "arenalist":
                if (!player.hasPermission("duel.use")) {
                    player.sendMessage("§cYou do not have permission to use duel commands.");
                    return true;
                }
                List<String> arenas = plugin.getArenaManager().getArenaNames();
                if (arenas.isEmpty()) {
                    player.sendMessage("§cNo arenas available.");
                } else {
                    player.sendMessage("§eAvailable Arenas:");
                    for (String a : arenas) {
                        player.sendMessage("§a- " + a);
                    }
                }
                break;
            case "stats":
                PlayerStats stats = plugin.getStatsManager().getStats(player);
                player.sendMessage("§e--- Duel Stats ---");
                player.sendMessage("§aWins: §f" + stats.getWins());
                player.sendMessage("§cLosses: §f" + stats.getLosses());
                player.sendMessage("§eWin/Loss Ratio: §f" + stats.getWinLossRatio());
                player.sendMessage("§eTotal Duels: §f" + stats.getTotalDuels());
                player.sendMessage("§6Coins: §f" + stats.getCoins());
                break;
            default:
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /duel [player] [arena]");
                    return true;
                }
                String targetName = args[0];
                arenaName = args[1];
                if (player.getName().equalsIgnoreCase(targetName)) {
                    player.sendMessage("§cYou cannot duel yourself.");
                    return true;
                }
                Player target = plugin.getServer().getPlayer(targetName);
                if (target == null) {
                    player.sendMessage("§cPlayer not found.");
                    return true;
                }
                if (plugin.getDuelManager().isInDuel(player) || plugin.getDuelManager().isInDuel(target)) {
                    player.sendMessage("§cOne of the players is already in a duel.");
                    return true;
                }
                plugin.getDuelManager().sendDuelRequest(player, target, arenaName);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!command.getName().equalsIgnoreCase("duel")) {
            return completions;
        }

        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("accept");
            subCommands.add("decline");
            subCommands.add("create");
            subCommands.add("setspawn1");
            subCommands.add("setspawn2");
            subCommands.add("arenalist");
            subCommands.add("stats");

            for (String sub : subCommands) {
                if (sub.startsWith(args[0].toLowerCase())) {
                    completions.add(sub);
                }
            }
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            if (subCommand.equals("accept") || subCommand.equals("decline")) {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    if (p.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(p.getName());
                    }
                }
            } else if (subCommand.equals("create") || subCommand.equals("setspawn1") || subCommand.equals("setspawn2")) {
                // No completions needed for arena name here
            } else {
                // Assume it's /duel [player] <arena>
                List<String> arenas = plugin.getArenaManager().getArenaNames();
                for (String arena : arenas) {
                    if (arena.toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(arena);
                    }
                }
            }
        }

        return completions;
    }
}
