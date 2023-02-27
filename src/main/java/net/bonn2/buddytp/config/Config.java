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

    @Comment({
            "This is a test comment",
            "with two lines"
    })
    public boolean test = true;

    public static void load() {
        instance = new Config();
        YamlConfigurations.save(configFile.toPath(), Config.class, instance);
        instance = YamlConfigurations.load(configFile.toPath(), Config.class);
    }
}
