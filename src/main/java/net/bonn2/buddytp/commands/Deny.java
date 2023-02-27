package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.util.BuddyTeleportRequest;
import net.bonn2.buddytp.util.BuddyTeleportRequests;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Deny implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        List<BuddyTeleportRequest> requests = BuddyTeleportRequests.getRequestsForPlayer(player);

        // Sender has no active requests
        if (requests.size() == 0) {
            sender.sendMessage("You do not have any pending requests.");
            return true;
        }

        // Sender has one active request
        if (requests.size() == 1) {
            requests.get(0).deny();
            return true;
        }

        // Sender has multiple active requests

        // Sender has not specified who to deny
        if (args.length == 0) {
            sender.sendMessage("You have multiple pending requests! Please specify which one you want to deny: /buddytpdeny <player>");
            return true;
        }

        // Sender has specified who to deny
        for (BuddyTeleportRequest request : requests) {
            if (request.getTargetPlayer().getName().equals(args[0])) {
                // Sender has specified this request
                request.deny();
                return true;
            }
        }

        // Sender specified a request that does not exist
        sender.sendMessage("You do not have an active request from the player " + args[0]);
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
