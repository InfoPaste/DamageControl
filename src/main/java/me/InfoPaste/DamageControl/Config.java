package me.InfoPaste.DamageControl;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Created by brookslab on 3/23/2016.
 */
public class Config {

    Main plugin;

    static Config instance = new Config();
    static Plugin p;
    public static FileConfiguration config;
    static File cfile;

    public void Config (Main plugin) {
        this.plugin = plugin;
    }

    public static void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "config.yml");

        if (!cfile.exists()) {
            cfile.getParentFile().mkdirs();
            p.saveResource("config.yml", false);
            //copy(p.getResource("config.yml"), cfile);
        }

        config = new YamlConfiguration();

        try {
            config.load(cfile);

            config = YamlConfiguration.loadConfiguration(cfile);

            config.options().copyDefaults(true);
            p.saveDefaultConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
