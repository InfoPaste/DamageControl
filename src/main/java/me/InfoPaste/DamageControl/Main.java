package me.InfoPaste.DamageControl;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        Config.setup(this);

        StringBuilder stringBuilder = new StringBuilder();
        for (World world : Bukkit.getWorlds()) {
            stringBuilder.append(world.getName()).append(", ");
        }
        String worldsFound = stringBuilder.toString();

        getLogger().info("Worlds: " + worldsFound.substring(0, worldsFound.length() - 2));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {

        DamageCause damageCause = event.getCause();

        String world = event.getEntity().getLocation().getWorld().getName();

        if (Config.config.contains(world)
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

                for (String damage : damages) {
                    if (damage.equalsIgnoreCase("all")) {
                        event.setCancelled(true);
                        return;
                    } else if (DamageCause.valueOf(damage.toUpperCase()) == damageCause) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
