package me.jay.appleuhc.listeners;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class onDeath implements Listener {

    private final AppleUHC plugin;
    public onDeath(AppleUHC plugin){
        this.plugin = plugin;
    }



    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (plugin.getAlive().containsKey(e.getPlayer().getUniqueId())){
            plugin.getAlive().remove(e.getPlayer().getUniqueId());
            plugin.getDead().put(e.getPlayer().getUniqueId(), "dead");
            Bukkit.broadcastMessage(ChatColor.GOLD + "[AppleUHC] " + ChatColor.YELLOW + e.getPlayer().getName() + " was elimated.");
            Bukkit.broadcastMessage(ChatColor.GOLD + "[AppleUHC] " + ChatColor.YELLOW + " " + plugin.getPlayersAlive() + " remain.");
        }

        if (plugin.getAlive().size() == 1){
            for (Map.Entry<UUID, String> entry : plugin.getAlive().entrySet()) {
                UUID key = entry.getKey();
                String value = entry.getValue();

                Player player = Bukkit.getPlayer(key);
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                Bukkit.broadcastMessage(ChatColor.GOLD + "[AppleUHC] " + ChatColor.YELLOW + " " + player.getName() + " was the last remaining!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 3, 4);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 2, 3, 4);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 1, 3, 2);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 3, 5);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 3, 5);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 2, 3);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 1, 3);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 3, 3, 2);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 2, 3, 0);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 1, 3, 0);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 7, 8, 0);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 1, 0, 1);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 2, 2, 2);
                    p.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 5, 4, 1, 10);
                    p.sendTitle(Color("&6" + player.getName()), Color(" &e1st Place!"), 20, 20, 20);
                    p.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 10, 5);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(console, "spawn " + p), 60*10);
                }

            }
        }


    }

    private String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }

}
