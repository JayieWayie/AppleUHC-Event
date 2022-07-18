package me.jay.appleuhc.commands;

import me.jay.appleuhc.AppleUHC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class xraytoggle implements CommandExecutor {

    private final AppleUHC plugin;
    public xraytoggle(AppleUHC plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player){

            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("auhc")) {
                if (args[0].equalsIgnoreCase("xray")) {

                    if (player.hasPermission("AppleUHC.xray.toggle")) {
                        if (!plugin.getAlerts().containsKey(player.getUniqueId())) {
                            plugin.getAlerts().put(player.getUniqueId(), true);
                            player.sendMessage(Color("&8[&6AppleUHC&8] &eXray Alerts Toggled On."));
                        } else {
                            plugin.getAlerts().remove(player.getUniqueId());
                            player.sendMessage(Color("&8[&6AppleUHC&8] &eXray Alerts Toggled Off."));
                        }
                    }else{
                        player.sendMessage(Color("&8[&6AppleUHC&8] &eYou do not have access to this command."));
                    }
                }
            }

        }

        return true;
    }

    private String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&', s);
        return s;
    }
}
