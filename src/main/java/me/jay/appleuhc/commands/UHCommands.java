package me.jay.appleuhc.commands;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class UHCommands implements CommandExecutor {

    private final AppleUHC plugin;
    public UHCommands(AppleUHC plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (command.getName().equalsIgnoreCase("uhc")){
                if (args[0].equalsIgnoreCase("join")){
                    Join(player);
                }else if (args[0].equalsIgnoreCase("start")){
                    if (player.hasPermission("appleuhc.start")){
                        Start(player);
                        heals();
                        border(console);
                        deathmatch();
                    }else{
                        player.sendMessage(Color("&8[&6AppleUHC&8] &eYou do not have access to this command."));
                    }
                }else if (args[0].equalsIgnoreCase("end")){
                    end(player);
                }else if (args[0].equalsIgnoreCase("revive")){
                    if (player.hasPermission("appleuhc.revive")) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            Revive(target, player);
                        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                            player.sendMessage(Color("&8[&6AppleUHC&8] &eYou must provide an online player."));
                        }
                    }else{
                        player.sendMessage(Color("&8[&6AppleUHC&8] &eYou do not have access to this command."));
                    }
                }

            }
        }


        return true;
    }
    private void Join(Player player){
        if (!plugin.gamestatus) {
            if(!plugin.getDead().containsKey(player.getUniqueId())) {
                player.sendMessage(Color("&8[&6AppleUHC&8] &eYou have joined the UHC Event."));
                plugin.getAlive().put(player.getUniqueId(), "alive");
            }else{
                player.sendMessage(Color("&8[&6AppleUHC&8] &eYou are dead."));
            }
        }else{
            player.sendMessage(Color("&8[&6AppleUHC&8] &eThe game has already begun."));
        }
    }
    private void Start(Player player){
        if (!plugin.gamestatus){
            Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eGame has begun!"));
            plugin.gamestatus = true;
            long time = 30 * 60 * 60;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (plugin.getAlive().containsKey(p.getUniqueId())){
                    p.performCommand("rtp");
                }
            }
        }else{
            player.sendMessage(Color("&8[&6AppleUHC&8] &eThe game has already begun."));
        }
    }
    private void Revive(Player target, Player player){
        if (plugin.getAlive().containsKey(target.getUniqueId())){
            player.sendMessage(Color("&8[&6AppleUHC&8] &eThat player is already alive."));
        }else if (plugin.getDead().containsKey(target.getUniqueId())){
            player.sendMessage(Color("&8[&6AppleUHC&8] &eThat player has now been revived."));
            plugin.getAlive().put(target.getUniqueId(), "alive");
            plugin.getDead().remove(target.getUniqueId());
        }else{
            player.sendMessage(Color("&8[&6AppleUHC&8] &eThat player is not part of the game."));
        }
    }
    private void end (Player player){
        plugin.gamestatus = false;
        plugin.getDead().clear();
        plugin.getAlive().clear();
    }
    private void heals(){
        for (Player p : Bukkit.getOnlinePlayers()){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> p.setHealth(20), 60*20*20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> p.setHealth(20), 60*40*20);
        }


    }
    private void border(ConsoleCommandSender console){
        Bukkit.dispatchCommand(console, "wb set UHC 5000 5000 0 0");
        Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eBorder will begin moving in 5minutes."));
        Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &cWARNING - &eBorder moves 1 block every 5 seconds."));
        for (int i = 5000; i > 250; i--) {
            int finalI = i;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.dispatchCommand(console, "wb set UHC " + finalI + " 0 0"), 20 * 300L, 20 * 5L);
       }
    }
    private void deathmatch(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 60 minutes.")), 60*20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 10 minutes.")), 60*50*20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 5 minutes.")), 60*55*20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 1 minute.")), 60*59*20);
    }

    private String Color(String s){ s = ChatColor.translateAlternateColorCodes('&',s); return s; }

}
