package net.bonn2.buddytp.commands;

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

public class Reset implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /resetbuddytp <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("Could not find player " + args[0] + ".");
            return true;
        }

        if (Data.hasBuddyTP(target)) {
            sender.sendMessage(args[0] + " already has their buddytp.");
            return true;
        }

        Data.resetBuddyTP(target);
        sender.sendMessage("Successfully reset " + args[0] + "'s buddytp");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String partialName = args[0];
            for (Player player : Bukkit.getOnlinePlayers()) {
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(partialName.toLowerCase())) {
                    completions.add(playerName);
                }
            }
        }
        return completions;
    }
}
