package net.bonn2.buddytp.util;

import org.bukkit.entity.Player;

/**
 * Represents a buddy teleport request from one player to another.
 */
public class BuddyTeleportRequest {
    private Player sender;
    private Player targetPlayer;
    private long creationTime;

    /**
     * Constructs a new buddy teleport request.
     * @param sender The player who sent the teleport request.
     * @param targetPlayer The player who is being asked to teleport.
     */
    public BuddyTeleportRequest(Player sender, Player targetPlayer) {
        this.sender = sender;
        this.targetPlayer = targetPlayer;
        creationTime = System.currentTimeMillis();
    }

    /**
     * Gets the player who sent the teleport request.
     * @return The sender.
     */
    public Player getSender() {
        return sender;
    }

    /**
     * Gets the player who is being asked to teleport.
     * @return The target player.
     */
    public Player getTargetPlayer() {
        return targetPlayer;
    }

    /**
     * Checks to see if this request is timed out.
     * Compares age to the configured max age and removes the
     * request from the list of pending requests if it is expired
     * @return True if the request is still active and
     *         False if the request is not active
     */
    public boolean isActive() {
        // TODO: Implementation
        return true;
    }

    /**
     * Accepts the buddy teleport request.
     * Teleports the sender to the location of the target player,
     * logs the teleport in the data file, and removes the request
     * from the list of pending requests.
     */
    public void accept() {
        targetPlayer.sendMessage("You have accepted a teleport request from %s".formatted(sender.getName()));
        sender.sendMessage("Your request to teleport to %s has been accepted.".formatted(targetPlayer.getName()));
        sender.teleport(targetPlayer.getLocation());
        Data.useBuddyTP(sender);
        BuddyTeleportRequests.removeRequest(this);
    }

    /**
     * Declines the buddy teleport request.
     * Notifies the sender that their request has been declined and
     * removes the request from the list of pending requests.
     */
    public void deny() {
        targetPlayer.sendMessage("You have declined a buddy teleport request from: " + sender.getName());
        sender.sendMessage("Your buddy teleport request has been declined.");
        BuddyTeleportRequests.removeRequest(this);
    }

    /**
     * Cancels a buddy teleport request.
     * Notifies the sender and target that the request has been canceled and
     * removes the request from the list of pending requests
     */
    public void cancel() {
        sender.sendMessage("Your buddy teleport request has been canceled.");
        targetPlayer.sendMessage("The teleport request from " + sender.getName() + " has been canceled.");
        BuddyTeleportRequests.removeRequest(this);
    }
}
