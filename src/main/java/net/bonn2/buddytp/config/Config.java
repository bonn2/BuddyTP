package net.bonn2.buddytp.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.Ignore;
import de.exlll.configlib.YamlConfigurations;
import net.bonn2.buddytp.BuddyTP;

import java.io.File;

@Configuration
public class Config {

    @Ignore
    public static Config instance;
    @Ignore
    private static final File configFile = new File(BuddyTP.plugin.getDataFolder() + File.separator + "config.yml");

    @Comment({"How long a buddytp request lasts before it times out (seconds)"})
    public int timeout = 60;

    public static void load() {
        instance = new Config();
        YamlConfigurations.update(configFile.toPath(), Config.class);
        instance = YamlConfigurations.load(configFile.toPath(), Config.class);
    }
}
