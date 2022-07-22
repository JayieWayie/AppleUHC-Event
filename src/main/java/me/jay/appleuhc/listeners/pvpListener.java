package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
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
                if (plugin.pvp == false) {
                    e.setCancelled(true);
                    ((Player) e.getDamager()).getPlayer().sendMessage("You cannot pvp yet.");
                }
            }
        }

    }
}
