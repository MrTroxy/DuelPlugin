package com.dev.mrtroxy.managers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dev.mrtroxy.DuelPlugin;
import com.dev.mrtroxy.models.Arena;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Manages the creation, loading, and saving of duel arenas.
 */
public class ArenaManager {

    private DuelPlugin plugin;
    private HashMap<String, Arena> arenas;

    public ArenaManager(DuelPlugin plugin) {
        this.plugin = plugin;
        this.arenas = new HashMap<>();

        // Load arenas from JSON file
        loadArenas();
    }

    /**
     * Loads arenas from the arenas.json file.
     */
    public void loadArenas() {
        File file = new File(plugin.getDataFolder(), "arenas.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                arenas = gson.fromJson(reader, new TypeToken<HashMap<String, Arena>>() {}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves arenas to the arenas.json file.
     */
    public void saveArenas() {
        File file = new File(plugin.getDataFolder(), "arenas.json");
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(arenas, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new arena with the given name, without setting spawn points.
     */
    public void createArena(String name) {
        arenas.put(name, new Arena(name, null, null));
        saveArenas();
    }

    /**
     * Retrieves an arena by name.
     */
    public Arena getArena(String name) {
        return arenas.get(name);
    }

    /**
     * Checks if an arena exists.
     */
    public boolean arenaExists(String name) {
        return arenas.containsKey(name);
    }

    /**
     * Gets the list of all arenas.
     */
    public List<String> getArenaNames() {
        return new ArrayList<>(arenas.keySet());
    }
}
