package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.util.BuddyTeleportRequest;
import net.bonn2.buddytp.util.BuddyTeleportRequests;
import net.bonn2.buddytp.util.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Request implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!Data.hasBuddyTP(player)) {
            sender.sendMessage("You have already used your buddytp!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /buddytp <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("Could not find player " + args[0] + ".");
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            sender.sendMessage("You cannot buddy teleport to yourself.");
            return true;
        }

        BuddyTeleportRequest request = new BuddyTeleportRequest(player, target);
        BuddyTeleportRequests.addRequest(request);

        target.sendMessage(player.getName() + " wants to buddy teleport to you. Type '/buddytpaccept' to accept or '/buddytpdeny' to decline.");
        player.sendMessage("Buddy teleport request sent to " + target.getName() + ".");

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
