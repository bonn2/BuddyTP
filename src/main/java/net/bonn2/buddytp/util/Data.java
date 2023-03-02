package net.bonn2.buddytp.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.bonn2.buddytp.BuddyTP;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {

    private static JsonArray playerData;

    /**
     * Loads player data from data.json into a JsonArray
     */
    public static void load() {
        File file = new File(BuddyTP.plugin.getDataFolder() + File.separator + "data.json");
        file.getParentFile().mkdirs();
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(reader, JsonElement.class);
            playerData = element.getAsJsonArray();
        } catch (IOException e) {
            playerData = new JsonArray(0);
        }
    }

    /**
     * Returns true if a player has used their buddy tp
     * @return True if the players UUID is present
     */
    public static boolean hasBuddyTP(Player player) {
        for (JsonElement element : playerData) {
            String playerUUID = element.getAsString();
            if (player.getUniqueId().toString().equals(playerUUID)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the players UUID to the loaded JsonArray then calls the save() function
     * @param player The player who used their buddytp
     */
    public static void useBuddyTP(@NotNull Player player) {
        playerData.add(player.getUniqueId().toString());
        save();
    }

    /**
     * Removes a players UUID from the loaded JsonArray then calls the save() function
     * @param player The player who's buddytp should be reset
     */
    public static void resetBuddyTP(@NotNull Player player) {
        playerData.remove(new JsonPrimitive(player.getUniqueId().toString()));
        save();
        player.sendMessage(Messages.get("your-buddy-tp-reset"));
    }

    /**
     * Saves the loaded JsonArray to the file data.json
     */
    private static void save() {
        try (FileWriter writer = new FileWriter(BuddyTP.plugin.getDataFolder() + File.separator + "data.json")) {
            Gson gson = new Gson();
            gson.toJson(playerData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}