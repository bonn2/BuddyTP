package net.bonn2.buddytp.util;

import net.bonn2.buddytp.BuddyTP;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of pending buddy teleport requests.
 */
public class BuddyTeleportRequests {
    private static final List<BuddyTeleportRequest> requests = new ArrayList<>();
    private static BukkitTask timeoutCheck;

    /**
     * Start the BukkitTask for checking for timed out requests, this should only be run <b>once</b> at startup
     * @param plugin The plugin the task will be registered to.
     */
    public static void startTimeoutCheck(BuddyTP plugin) {
        if (timeoutCheck == null || timeoutCheck.isCancelled()) {
            timeoutCheck = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < requests.size(); i++) {
                        if (!requests.get(i).isActive()) {
                            requests.get(i).timeout();
                            i--;
                        }
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
        } else {
            plugin.getLogger().warning("BuddyTeleportRequests#startTimeoutCheck has been called multiple times! This is not supported behaviour!");
        }
    }

    /**
     * Adds a new buddy teleport request to the list of pending requests.
     * @param request The request to add.
     */
    public static void addRequest(BuddyTeleportRequest request) {
        requests.add(request);
    }

    /**
     * Removes a buddy teleport request from the list of pending requests.
     * @param request The request to remove.
     */
    public static void removeRequest(BuddyTeleportRequest request) {
        requests.remove(request);
    }

    /**
     * Gets a list of all pending buddy teleport requests for a specific player.
     * @param player The player to get requests for.
     * @return A list of all pending requests for the specified player.
     */
    public static @NotNull List<BuddyTeleportRequest> getRequestsForPlayer(Player player) {
        List<BuddyTeleportRequest> playerRequests = new ArrayList<>();
        for (BuddyTeleportRequest request : requests) {
            if (request.getTargetPlayer().equals(player)) {
                playerRequests.add(request);
            }
        }
        return playerRequests;
    }

    /**
     * Get a request made by a player
     * @param player The player who made the request
     * @return The request they made, null if there is no request
     */
    public static @Nullable BuddyTeleportRequest getRequestFromPlayer(Player player) {
        for (BuddyTeleportRequest request : requests) {
            if (request.getSender().equals(player)) {
                return request;
            }
        }
        return null;
    }
}
