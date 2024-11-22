package com.dev.mrtroxy.utils;

/**
 * Represents a player's duel statistics.
 */
public class PlayerStats {

    private int wins;
    private int losses;
    private int coins;

    public PlayerStats() {
        this.wins = 0;
        this.losses = 0;
        this.coins = 0;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getCoins() {
        return coins;
    }

    public double getWinLossRatio() {
        return losses == 0 ? wins : (double) wins / losses;
    }

    public int getTotalDuels() {
        return wins + losses;
    }
}
