package me.jay.appleuhc.commands;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;


public class UHCommands implements CommandExecutor {

    int taskID;

    int timerTask;
    int deathmatchTask;
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
                try {
                    if (args[0].equalsIgnoreCase("join")) {
                        Join(player);
                    } else if (args[0].equalsIgnoreCase("start")) {
                        if (player.hasPermission("appleuhc.start")) {
                            Start(player);
                            heals();
                            border(console);
                            deathmatch(console);
                            timer();
                            enablePvp();
                            boosters(player, console);
                        } else {
                            player.sendMessage(Color("&8[&6AppleUHC&8] &eYou do not have access to this command."));
                        }
                    }else if (args[0].equalsIgnoreCase("revive")) {
                        if (player.hasPermission("appleuhc.revive")) {
                            try {
                                Player target = Bukkit.getPlayer(args[1]);
                                Revive(target, player);
                            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                                player.sendMessage(Color("&8[&6AppleUHC&8] &eYou must provide an online player."));
                            }
                        } else {
                            player.sendMessage(Color("&8[&6AppleUHC&8] &eYou do not have access to this command."));
                        }
                    }
                }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
                    player.sendMessage(Color("&8[&6AppleUHC&8] &eYou can do '/uhc join' to join."));
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

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAll players were healed.")), 60*40*20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAll players were healed.")), 60*20*20);

    }
    private void border(ConsoleCommandSender console){
        Bukkit.dispatchCommand(console, "wb UHC set 2500 2500 0 0");
        Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eBorder will begin moving in 5minutes."));
        Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &cWARNING - &eBorder moves 5 block every 5 seconds."));
        Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &cPVP Enabled in 15 minutes. Prepare to fight!"));



        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {



            Bukkit.dispatchCommand(console, "wb UHC set " + plugin.x + " " + plugin.z + " 0 0");

            if (plugin.x == 250 || plugin.z == 250){
                Bukkit.getScheduler().cancelTask(taskID);
            }

            plugin.x = plugin.x - 5;
            plugin.z = plugin.z - 5;

        }, 20*300L, 20L * 5);
    }

    private void timer(){

        BukkitScheduler scheduler2 = Bukkit.getServer().getScheduler();
        timerTask = scheduler2.scheduleSyncRepeatingTask(plugin, () -> {

            plugin.time = plugin.time + 1;


            int timer = plugin.time;
            int hours = timer / 3600, remainder = timer % 3600, minutes = remainder / 60, seconds = remainder % 60;
            String disHour = (hours < 10 ? "0" : "") + hours, disMinu = (minutes < 10 ? "0" : "") + minutes, disSec = (seconds < 10 ? "0" : "") + seconds;
            plugin.formattedTime = disHour + ":" + disMinu + ":" + disSec;

        }, 1L, 20L);



    }

    public void boosters(Player player, ConsoleCommandSender console){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAlive players will recieve care-packages at 15 minutes, 30 minutes, 45 minutes and 55 minutes.")), 5 * 60 * 3);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eKeep an eye on the Timer on the scoreboard to see when the next one is.")), 5 * 60 * 3);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (plugin.getAlive().containsKey(p.getUniqueId())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(console, "give " + p.getName() + " cooked_beef 10"), 60 * 15 * 20);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(console, "give " + p.getName() + " oak_wood 3"), 20 * 60 * 30);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(console, "give " + p.getName() + " golden_apple 1"), 60 * 12 * 45);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(console, "give " + p.getName() + " diamond 2"), 20 * 60 * 55);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAlive players recieved a care-package!")), 15 * 60 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAlive players recieved a care-package!")), 30 * 60 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAlive players recieved a care-package!")), 45 * 60 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eAlive players recieved a care-package!")), 60 * 55 * 20);
    }

    private void enablePvp(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.pvp = true, 60*20*15);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &ePVP is now Enabled.")), 60*20*15);
    }

    private void deathmatch(ConsoleCommandSender console) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 60 minutes.")), 60 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 10 minutes.")), 60 * 50 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 5 minutes.")), 60 * 55 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.broadcastMessage(Color("&8[&6AppleUHC&8] &eDeathmatch will start in 1 minute.")), 60 * 59 * 20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.getScheduler().cancelTask(taskID), 60 * 60 * 20);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (plugin.getAlive().containsKey(p.getUniqueId())){
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> p.teleport(new Location(p.getWorld(), 15, 83 , 6)), 60*20*60);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.deathmatch = true, 60*20*60);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> deathmathborder(console), 60*20*60);
    }

    public void deathmathborder(ConsoleCommandSender console){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        deathmatchTask = scheduler.scheduleSyncRepeatingTask(plugin, () -> {



            Bukkit.dispatchCommand(console, "wb UHC set " + plugin.deathmatchx + " " + plugin.deathmatchy + " 0 0");

            if (plugin.deathmatchx == 10 || plugin.deathmatchy == 10){
                Bukkit.getScheduler().cancelTask(deathmatchTask);
            }

            plugin.deathmatchx = plugin.deathmatchx - 5;
            plugin.deathmatchy = plugin.deathmatchy - 5;

        }, 20*5, 20L * 5);
    }


    private String Color(String s){ s = ChatColor.translateAlternateColorCodes('&',s); return s; }




}
