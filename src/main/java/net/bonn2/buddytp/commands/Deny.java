package net.bonn2.buddytp.commands;

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

public class Deny implements CommandExecutor, TabCompleter {

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
            requests.get(0).deny();
            return true;
        }

        // Sender has multiple active requests

        // Sender has not specified who to deny
        if (args.length == 0) {
            sender.sendMessage(Messages.get("multiple-pending"));
            return true;
        }

        // Sender has specified who to deny
        for (BuddyTeleportRequest request : requests) {
            if (request.getTarget().getName().equals(args[0])) {
                // Sender has specified this request
                request.deny();
                return true;
            }
        }

        // Sender specified a request that does not exist
        Map<String, String> placeholders = new HashMap<>(2);
        placeholders.put("%sender%", args[0]);
        placeholders.put("%target%", sender.getName());
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
