package me.jay.appleuhc.listeners;

import io.papermc.paper.event.block.BlockBreakBlockEvent;
import me.jay.appleuhc.AppleUHC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class blockbreak implements Listener {

    private final AppleUHC plugin;
    public blockbreak(AppleUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player player = e.getPlayer();

        if (e.getBlock().getType().equals(Material.DIAMOND_ORE)){
            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.hasPermission("AppleUHC.xray.alerts")){
                    if (plugin.getAlerts().containsKey(p.getUniqueId())){
                        TextComponent message = new TextComponent(Color("&8[&6AppleUHC&8] &e" + e.getPlayer() + " has mined +1 Diamonds."));
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp " + e.getPlayer()));
                        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to tp to user.").create()));
                        p.spigot().sendMessage(message);
                    }
                }
            }
        }

    }

    private String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
