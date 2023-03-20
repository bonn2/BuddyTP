package net.bonn2.buddytp;

import net.bonn2.buddytp.commands.*;
import net.bonn2.buddytp.config.Config;
import net.bonn2.buddytp.util.BuddyTeleportRequests;
import net.bonn2.buddytp.util.Data;
import net.bonn2.buddytp.util.Messages;
import net.bonn2.buddytp.util.ModrinthUpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BuddyTP extends JavaPlugin {

    public static BuddyTP plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Init data from file
        Config.load();
        Data.load();
        Messages.load();

        // Check if supported sethome plugin is installed
        if (Config.instance.setHome) {
            if (getServer().getPluginManager().isPluginEnabled("Essentials")) {
                getLogger().info("Enabling Essentials Hook");
            } else {
                getLogger().warning("Sethome option enabled but no supported sethome plugin detected!");
                Config.instance.setHome = false;
            }
        }

        // Register commands
        Objects.requireNonNull(getCommand("buddytp")).setExecutor(new Request());
        Objects.requireNonNull(getCommand("buddytp")).setTabCompleter(new Request());
        Objects.requireNonNull(getCommand("buddytpaccept")).setExecutor(new Accept());
        Objects.requireNonNull(getCommand("buddytpaccept")).setTabCompleter(new Accept());
        Objects.requireNonNull(getCommand("buddytpdeny")).setExecutor(new Deny());
        Objects.requireNonNull(getCommand("buddytpdeny")).setTabCompleter(new Deny());
        Objects.requireNonNull(getCommand("buddytpcancel")).setExecutor(new Cancel());
        Objects.requireNonNull(getCommand("buddytpcancel")).setTabCompleter(new Cancel());
        Objects.requireNonNull(getCommand("resetbuddytp")).setExecutor(new Reset());
        Objects.requireNonNull(getCommand("resetbuddytp")).setTabCompleter(new Reset());
        Objects.requireNonNull(getCommand("buybuddytp")).setExecutor(new Buy());
        Objects.requireNonNull(getCommand("buybuddytp")).setTabCompleter(new Buy());

        // Start recurring tasks
        BuddyTeleportRequests.startTimeoutCheck(this);

        // Enable bStats
        if (Config.instance.enableBStats) {
            getLogger().info("Enabling bStats, thanks it really helps!");
            new Metrics(this, 18019);
        }

        // Check for updates
        if (Config.instance.checkForUpdates) {
            ModrinthUpdateChecker.check(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
