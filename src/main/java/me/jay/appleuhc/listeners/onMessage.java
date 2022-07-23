package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onMessage implements Listener {

    private final AppleUHC plugin;
    public onMessage(AppleUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent e){
        if (plugin.getDead().containsKey(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Color("&8[&6AppleUHC&8] &eYou may not talk whilst dead."));
        }
    }

    private String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
