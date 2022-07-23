package me.jay.appleuhc;

import it.unimi.dsi.fastutil.Hash;
import me.jay.appleuhc.commands.UHCommands;
import me.jay.appleuhc.commands.xraytoggle;
import me.jay.appleuhc.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class AppleUHC extends JavaPlugin {

    public int x = 2500;
    public int z = 2500;

    public int deathmatchx = 250;
    public int deathmatchy = 250;
    public boolean deathmatch = false;

    public String formattedTime = "null";

    public int time = 1;
    private static HashMap<UUID, Boolean> alerts = new HashMap<>();
    private static HashMap<UUID, String> alive = new HashMap<>();
    private static HashMap<UUID, String> dead = new HashMap<>();

    public boolean gamestatus = false;
    public boolean pvp = false;

    public HashMap<UUID, Boolean> getAlerts() {
        return alerts;
    }




    public HashMap<UUID, String> getDead(){
        return dead;
    }

    public HashMap<UUID, String> getAlive(){
        return alive;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new blockbreak(this), this);
        getCommand("auhc").setExecutor(new xraytoggle(this));
        getCommand("uhc").setExecutor(new UHCommands(this));
        getServer().getPluginManager().registerEvents(new logOut(this), this);
        getServer().getPluginManager().registerEvents(new joinEvent(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(this), this);
        getServer().getPluginManager().registerEvents(new pvpListener(this), this);
        getServer().getPluginManager().registerEvents(new onMessage(this), this);
        new placeholder(this).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
