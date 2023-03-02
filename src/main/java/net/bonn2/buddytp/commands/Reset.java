package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.util.Data;
import net.bonn2.buddytp.util.Messages;
import org.bukkit.Bukkit;
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

public class Reset implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        Map<String, String> placeholders = new HashMap<>(2);
        placeholders.put("%target%", args[0]);

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Messages.get("could-not-find-player", placeholders));
            return true;
        }

        // Update placeholder to get correct capitalization
        placeholders.put("%target%", target.getName());

        if (Data.hasBuddyTP(target)) {
            sender.sendMessage(Messages.get("already-has-buddy-tp", placeholders));
            return true;
        }

        Data.resetBuddyTP(target);
        sender.sendMessage(Messages.get("reset-buddy-tp", placeholders));
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
