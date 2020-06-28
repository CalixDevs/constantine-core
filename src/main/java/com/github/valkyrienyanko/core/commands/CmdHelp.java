package com.github.valkyrienyanko.core.commands;

import com.github.valkyrienyanko.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("help")) {
            if (!Core.mainConfig.getBoolean("help_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Core.messagesConfig.getString("command_disabled")));
                return true;
            }

            for (String line : Core.messagesConfig.getStringList("help"))
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

            return true;
        }

        return false;
    }
}
