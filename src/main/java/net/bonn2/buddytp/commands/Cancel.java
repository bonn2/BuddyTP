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

public class Cancel implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        BuddyTeleportRequest request = BuddyTeleportRequests.getRequestFromPlayer(player);

        // Sender has no active requests
        if (request == null) {
            sender.sendMessage("You do not have any pending requests.");
            return true;
        }

        request.cancel();
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        return new ArrayList<>();
    }
}
