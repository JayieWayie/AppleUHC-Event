package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class logOut implements Listener {

    private final AppleUHC plugin;
    public logOut(AppleUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogOut(PlayerQuitEvent e){
        if (plugin.getAlive().containsKey(e.getPlayer().getUniqueId())){
            plugin.getDead().put(e.getPlayer().getUniqueId(), "quit");
            plugin.getAlive().remove(e.getPlayer().getUniqueId());
        }
    }

}
