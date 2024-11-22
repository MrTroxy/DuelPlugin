package com.dev.mrtroxy.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * A serializable version of the Location class for saving to JSON.
 */
public class SerializableLocation {

    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public SerializableLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    public Location toLocation() {
        World w = Bukkit.getWorld(world);
        if (w == null) {
            return null;
        }
        return new Location(w, x, y, z, yaw, pitch);
    }
}
