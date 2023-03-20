package net.bonn2.buddytp.commands;

import net.bonn2.buddytp.config.Config;
import net.bonn2.buddytp.util.Data;
import net.bonn2.buddytp.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buy implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.get("only-players"));
            return true;
        }

        Map<String, String> placeholders = new HashMap<>(2);
        placeholders.put("%target%", player.getName());

        if (Data.hasBuddyTP(player)) {
            sender.sendMessage(Messages.get("you-already-have-buddy-tp", placeholders));
            return true;
        }

        ItemStack mainItem = player.getInventory().getItemInMainHand();
        ItemStack offItem = player.getInventory().getItemInOffHand();
        if (mainItem.asOne().equals(Config.instance.price.asOne())) {
            if (mainItem.getAmount() >= Config.instance.price.getAmount()) {
                ItemStack newItem = mainItem.asQuantity(mainItem.getAmount() - Config.instance.price.getAmount());
                player.getInventory().setItemInMainHand(newItem);
                Data.resetBuddyTP(player);
                sender.sendMessage(Messages.get("bought-buddy-tp", placeholders));
                return true;
            }
        } else if (offItem.asOne().equals(Config.instance.price.asOne())) {
            if (offItem.getAmount() >= Config.instance.price.getAmount()) {
                ItemStack newItem = offItem.asQuantity(offItem.getAmount() - Config.instance.price.getAmount());
                player.getInventory().setItemInOffHand(newItem);
                Data.resetBuddyTP(player);
                sender.sendMessage(Messages.get("bought-buddy-tp", placeholders));
                return true;
            }
        }
        sender.sendMessage(Messages.get("price-too-high", placeholders));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        return new ArrayList<>();
    }
}
