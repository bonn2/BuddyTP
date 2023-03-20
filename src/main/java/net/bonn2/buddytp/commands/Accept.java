package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.config.Config;
import net.bonn2.buddytp.util.BuddyTeleportRequest;
import net.bonn2.buddytp.util.BuddyTeleportRequests;
import net.bonn2.buddytp.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accept implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.get("only-players"));
            return true;
        }

        List<BuddyTeleportRequest> requests = BuddyTeleportRequests.getRequestsForPlayer(player);

        // Sender has no active requests
        if (requests.size() == 0) {
            sender.sendMessage(Messages.get("no-pending-requests"));
            return true;
        }

        // Sender has one active request
        if (requests.size() == 1) {
            Map<String, String> placeholders = new HashMap<>(3);
            placeholders.put("%sender%", requests.get(0).getSender().getName());
            placeholders.put("%target%", player.getName());
            if (!Config.instance.teleportAcrossWords && !player.getWorld().equals(requests.get(0).getSender().getWorld())) {
                sender.sendMessage(Messages.get("different-world", placeholders));
                return true;
            }

            if (Config.instance.enableAllowlist && !Config.instance.allowedWorlds.contains(player.getWorld().getName())) {
                sender.sendMessage(Messages.get("not-in-this-world", placeholders));
                return true;
            }

            if (Config.instance.enableDenyList && Config.instance.deniedWorlds.contains(player.getWorld().getName())) {
                sender.sendMessage(Messages.get("not-in-this-world", placeholders));
                return true;
            }

            requests.get(0).accept();
            return true;
        }

        // Sender has multiple active requests

        // Sender has not specified who to accept
        if (args.length == 0) {
            sender.sendMessage(Messages.get("multiple-pending-requests"));
            return true;
        }

        // Sender has specified who to accept
        for (BuddyTeleportRequest request : requests) {
            if (request.getTargetPlayer().getName().equals(args[0])) {
                Map<String, String> placeholders = new HashMap<>(3);
                placeholders.put("%sender%", request.getSender().getName());
                placeholders.put("%target%", player.getName());
                if (!Config.instance.teleportAcrossWords && !player.getWorld().equals(request.getSender().getWorld())) {
                    sender.sendMessage(Messages.get("different-world", placeholders));
                    return true;
                }

                if (Config.instance.enableAllowlist && !Config.instance.allowedWorlds.contains(player.getWorld().getName())) {
                    sender.sendMessage(Messages.get("not-in-this-world", placeholders));
                    return true;
                }

                if (Config.instance.enableDenyList && Config.instance.deniedWorlds.contains(player.getWorld().getName())) {
                    sender.sendMessage(Messages.get("not-in-this-world", placeholders));
                    return true;
                }

                // Sender has specified this request
                request.accept();
                return true;
            }
        }

        Map<String, String> placeholders = new HashMap<>(2);
        placeholders.put("%target%", sender.getName());
        placeholders.put("%sender%", args[0]);

        // Sender specified a request that does not exist
        sender.sendMessage(Messages.get("no-active-request", placeholders));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<Player> activeRequests = new ArrayList<>();
            for (BuddyTeleportRequest request : BuddyTeleportRequests.getRequestsForPlayer((Player) sender))
                activeRequests.add(request.getSender());
            String partialName = args[0];
            for (Player player : activeRequests) {
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(partialName.toLowerCase())) {
                    completions.add(playerName);
                }
            }
        }
        return completions;
    }
}
