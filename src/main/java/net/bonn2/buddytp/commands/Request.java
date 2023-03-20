package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.config.Config;
import net.bonn2.buddytp.util.BuddyTeleportRequest;
import net.bonn2.buddytp.util.BuddyTeleportRequests;
import net.bonn2.buddytp.util.Data;
import net.bonn2.buddytp.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.get("only-players"));
            return true;
        }

        if (!Data.hasBuddyTP(player)) {
            sender.sendMessage(Messages.get("used-buddy-tp"));
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        Map<String, String> placeholders = new HashMap<>(3);
        placeholders.put("%sender%", sender.getName());
        placeholders.put("%target%", args[0]);

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Messages.get("could-not-find-player", placeholders));
            return true;
        }

        // Update target placeholder to get correct capitalization
        placeholders.put("%target%", target.getName());

        if (target.getUniqueId().equals(player.getUniqueId())) {
            sender.sendMessage(Messages.get("teleport-to-self", placeholders));
            return true;
        }

        if (!Config.instance.teleportAcrossWords && !player.getWorld().equals(target.getWorld())) {
            sender.sendMessage(Messages.get("different-world", placeholders));
            return true;
        }

        BuddyTeleportRequest request = new BuddyTeleportRequest(player, target);
        BuddyTeleportRequests.addRequest(request);

        // Add timeout placeholder
        placeholders.put("%timeout%", String.valueOf(Config.instance.timeout));

        target.sendMessage(Messages.get("request-target", placeholders));
        player.sendMessage(Messages.get("request-sender", placeholders));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String partialName = args[0];
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player == sender) continue;
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(partialName.toLowerCase())) {
                    completions.add(playerName);
                }
            }
        }
        return completions;
    }
}
