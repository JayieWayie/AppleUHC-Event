package me.jay.appleuhc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;



public class placeholder extends PlaceholderExpansion {

    private final AppleUHC plugin;
    public placeholder(AppleUHC plugin){
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Jay";
    }

    @Override
    public String getIdentifier() {
        return "appleuhc";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("status")){
            if (plugin.getDead().containsKey(player.getUniqueId())){
                return "&cDead";
            }else if (plugin.getAlive().containsKey(player.getUniqueId())){
                return "&cAlive";
            }else{
                return "Null";
            }
        }else if(params.equalsIgnoreCase("competitors")) {
            return String.valueOf(plugin.getAlive().size());
        }else if (params.equalsIgnoreCase("pvpstatus")){
            return String.valueOf(plugin.pvp);
        }else if (params.equalsIgnoreCase("border")){
            if (!plugin.deathmatch){
                return plugin.x + " x " + plugin.deathmatchy;
            }else{
                return plugin.deathmatchy + " x " + plugin.deathmatchx;
            }
        }else if (params.equalsIgnoreCase("timer")){
            return plugin.formattedTime;
        }

        return null; // Placeholder is unknown by the Expansion
    }

}
