package net.bonn2.buddytp.util;

import com.earth2me.essentials.IEssentials;
import net.bonn2.buddytp.BuddyTP;
import net.bonn2.buddytp.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static net.bonn2.buddytp.BuddyTP.plugin;

/**
 * Represents a buddy teleport request from one player to another.
 */
public class BuddyTeleportRequest {
    private final Player sender;
    private final Player targetPlayer;
    private final long creationTime;

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
    public Player getTarget() {
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
        return (System.currentTimeMillis() - creationTime) / 1000 < Config.instance.timeout;
    }

    /**
     * Accepts the buddy teleport request.
     * Teleports the sender to the location of the target player,
     * logs the teleport in the data file, and removes the request
     * from the list of pending requests.
     */
    public void accept() {
        sender.teleportAsync(targetPlayer.getLocation()).thenAccept(a -> {
            if (BuddyTP.IS_FOLIA) {
                Bukkit.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> acceptStepTwo());
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        acceptStepTwo();
                    }
                }.runTask(plugin);
            }
        });
    }

    /**
     * The second step for accepting a teleport request.
     * This method is called by accept()
     */
    protected void acceptStepTwo() {
        targetPlayer.sendMessage(Messages.get("accepted-target", getPlaceholders()));
        sender.sendMessage(Messages.get("accepted-sender", getPlaceholders()));
        Data.useBuddyTP(sender);
        if (Config.instance.setHome) {
            IEssentials essentials = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
            if (essentials != null) {
                if (essentials.getUser(sender).getHomes().size() == 0) {
                    essentials.getUser(sender).setHome("home", sender.getLocation());
                    sender.sendMessage(Messages.get("home-set"));
                }
            }
        }
        BuddyTeleportRequests.removeRequest(this);
    }

    /**
     * Declines the buddy teleport request.
     * Notifies the sender that their request has been declined and
     * removes the request from the list of pending requests.
     */
    public void deny() {
        targetPlayer.sendMessage(Messages.get("denied-target", getPlaceholders()));
        sender.sendMessage(Messages.get("denied-sender", getPlaceholders()));
        BuddyTeleportRequests.removeRequest(this);
    }

    /**
     * Cancels a buddy teleport request.
     * Notifies the sender and target that the request has been canceled and
     * removes the request from the list of pending requests
     */
    public void cancel() {
        targetPlayer.sendMessage(Messages.get("canceled-target", getPlaceholders()));
        sender.sendMessage(Messages.get("canceled-sender", getPlaceholders()));
        BuddyTeleportRequests.removeRequest(this);
    }

    /**
     * Times out a buddy teleport request.
     * Notifies the sender and target that the request has timed out and
     * removes the request from the list of pending requests
     */
    public void timeout() {
        sender.sendMessage(Messages.get("timed-out-sender", getPlaceholders()));
        targetPlayer.sendMessage(Messages.get("timed-out-target", getPlaceholders()));
        BuddyTeleportRequests.removeRequest(this);
    }

    private @NotNull Map<String, String> getPlaceholders() {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%target%", targetPlayer.getName());
        placeholders.put("%sender%", sender.getName());
        return placeholders;
    }
}
