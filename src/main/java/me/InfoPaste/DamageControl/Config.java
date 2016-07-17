package me.InfoPaste.DamageControl;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

class Config {

    public static FileConfiguration config;

    public static void setup(Plugin p) {
        File cfile = new File(p.getDataFolder(), "config.yml");

        if (!cfile.exists()) {
            p.saveResource("config.yml", false);
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
