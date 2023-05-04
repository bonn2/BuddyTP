package net.bonn2.buddytp.listeners;

import net.bonn2.buddytp.util.BuddyTeleportRequests;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        // Cancel all buddytp requests relating to players that disconnect
        BuddyTeleportRequests.cancelAllRequestsForPlayer(event.getPlayer());
    }
}
