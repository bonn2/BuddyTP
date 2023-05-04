package net.bonn2.buddytp.config;

import de.exlll.configlib.*;
import net.bonn2.buddytp.BuddyTP;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

@Configuration
public class Config {

    @Ignore
    public static Config instance;
    @Ignore
    private static final File configFile = new File(BuddyTP.plugin.getDataFolder() + File.separator + "config.yml");

    @Comment({"How long a BuddyTp request lasts before it times out (seconds)"})
    public int timeout = 60;

    @Comment({"What item buying a new buddy tp costs."})
    public ItemStack price = new ItemStack(Material.DIAMOND_BLOCK, 1);

    @Comment({"How many teleports a player gets."})
    public int amountOfTeleports = 1;

    @Comment({
            "How long until a players teleports reset (seconds)",
            "Set to -1 to make teleports not auto-reset"
    })
    public int resetAfter = -1;

    @Comment({"If players should be allowed to teleport to different worlds."})
    public boolean teleportAcrossWords = false;

    @Comment({"If BuddyTp should limit the worlds to the allowlist."})
    public boolean enableAllowlist = false;

    @Comment({"The worlds that the plugin will allow (only active if above option is true)"})
    public List<String> allowedWorlds = List.of("world", "world_nether", "world_the_end");

    @Comment({"If BuddyTp should prevent use in the denylist."})
    public boolean enableDenyList = false;

    @Comment({"The worlds that the plugin will deny (only active if above option is true)"})
    public List<String> deniedWorlds = List.of("denied_world");

    @Comment({"If BuddyTp should attempt to set the players home if they do not have one."})
    public boolean setHome = false;

    @Comment({"Should BuddyTP check for updates on startup. It will not auto-download the update"})
    public boolean checkForUpdates = true;

    @Comment({"Set this to false to disable bStats usage reporting."})
    public boolean enableBStats = true;

    public static void load() {
        instance = new Config();
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
                .header("""
                        BuddyTP config
                        
                        """)
                .build();
        instance = YamlConfigurations.update(configFile.toPath(), Config.class, properties);
    }
}
