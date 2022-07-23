package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class pvpListener implements Listener {

    private final AppleUHC plugin;
    public pvpListener(AppleUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){

        if (e.getDamager() instanceof Player){
            if (e.getEntity() instanceof Player){
                if (!plugin.pvp) {
                    e.setCancelled(true);
                    ((Player) e.getDamager()).getPlayer().sendMessage(Color("&8[&6AppleUHC&8] &eYou may not pvp yet."));
                }
            }
        }

    }

    private String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
