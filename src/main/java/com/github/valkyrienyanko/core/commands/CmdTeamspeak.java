package com.github.valkyrienyanko.core.commands;

import com.github.valkyrienyanko.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdTeamspeak implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("teamspeak")) {
            if (!Core.mainConfig.getBoolean("teamspeak_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Core.messagesConfig.getString("command_disabled")));
                return true;
            }

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Core.messagesConfig.getString("teamspeak")));
            return true;
        }

        return false;
    }
}
