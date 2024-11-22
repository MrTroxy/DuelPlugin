package com.dev.mrtroxy.models;

import com.dev.mrtroxy.utils.SerializableLocation;
import org.bukkit.Location;

/**
 * Represents a duel arena with two spawn points.
 */
public class Arena {

    private String name;
    private SerializableLocation spawnPoint1;
    private SerializableLocation spawnPoint2;

    public Arena(String name, Location spawnPoint1, Location spawnPoint2) {
        this.name = name;
        this.spawnPoint1 = spawnPoint1 != null ? new SerializableLocation(spawnPoint1) : null;
        this.spawnPoint2 = spawnPoint2 != null ? new SerializableLocation(spawnPoint2) : null;
    }

    public String getName() {
        return name;
    }

    public Location getSpawnPoint1() {
        return spawnPoint1 != null ? spawnPoint1.toLocation() : null;
    }

    public Location getSpawnPoint2() {
        return spawnPoint2 != null ? spawnPoint2.toLocation() : null;
    }

    public void setSpawnPoint1(Location loc) {
        this.spawnPoint1 = new SerializableLocation(loc);
    }

    public void setSpawnPoint2(Location loc) {
        this.spawnPoint2 = new SerializableLocation(loc);
    }
}
