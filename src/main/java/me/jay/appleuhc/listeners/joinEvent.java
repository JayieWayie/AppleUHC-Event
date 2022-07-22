package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class joinEvent implements Listener {

    private final AppleUHC plugin;
    public joinEvent(AppleUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (plugin.getDead().containsKey(e.getPlayer().getUniqueId())){
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
