package me.InfoPaste.DamageControl;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public static Main plugin;

    String worldNames[];

    @Override
    public void onEnable() {

        Config.setup(this);

        worldNames = new String[Bukkit.getServer().getWorlds().size()];

        int count = 0;

        for (World w : Bukkit.getServer().getWorlds()) {
            worldNames[count] = w.getName();
            count++;
        }

        for (String s : worldNames) {
            getLogger().info("World Found: " + s);
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {


        EntityDamageEvent.DamageCause damageCause = event.getCause();
        String world = event.getEntity().getLocation().getWorld().getName();

        if (Config.config.contains(event.getEntity().getLocation().getWorld().getName())
                || Config.config.contains("ALL")) {

            String path;

            if (event.getEntity() instanceof Player) {
                path = ".Player";
            } else {
                path = ".Entity";
            }

            if (Config.config.contains(world + path)) {

                List<String> damages = Config.config.getStringList(world + path);

                if (damages == null || damages.size() == 0) {
                    return;
                }

                for (int i = 0; i < damages.size(); i++) {

                    if (damages.get(i).equalsIgnoreCase(null)) {
                    } else if (damages.get(i).equalsIgnoreCase("all")) {
                        event.setCancelled(true);
                        return;
                    } else if (EntityDamageEvent.DamageCause.valueOf(damages.get(i).toUpperCase()) == damageCause) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
