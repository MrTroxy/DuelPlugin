package com.dev.mrtroxy.utils;

import java.util.UUID;

/**
 * Represents a duel request between two players.
 */
public class DuelRequest {

    private UUID sender;
    private UUID target;
    private String arenaName;

    public DuelRequest(UUID sender, UUID target, String arenaName) {
        this.sender = sender;
        this.target = target;
        this.arenaName = arenaName;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getTarget() {
        return target;
    }

    public String getArenaName() {
        return arenaName;
    }
}
