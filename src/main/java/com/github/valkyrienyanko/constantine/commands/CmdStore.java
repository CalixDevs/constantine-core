package com.github.valkyrienyanko.constantine.commands;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdStore implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("store")) {
            if (!Root.mainConfig.getBoolean("store_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("command_disabled")));
                return true;
            }

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("store")));
            return true;
        }

        return false;
    }
}
