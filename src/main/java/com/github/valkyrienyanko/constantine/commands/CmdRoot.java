package com.github.valkyrienyanko.constantine.commands;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdRoot implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("root")) {
            if (strings.length > 0 && strings[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("root.reload")) {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("no_permission")));
                    return true;
                }

                Root.mainCM.reloadConfig();
                Root.messagesCM.reloadConfig();

                if (Root.broadcast) {
                    Bukkit.getScheduler().cancelTask(Root.broadcastId);

                    Root.broadcast = false;
                    Root.broadcastId = 0;
                    Root.INSTANCE.broadcastMessages();
                }

                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("reload_success")));
            }

            return true;
        }

        return false;
    }
}


