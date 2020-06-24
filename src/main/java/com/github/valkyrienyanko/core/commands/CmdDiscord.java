package com.github.valkyrienyanko.core.commands;

import com.github.valkyrienyanko.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("discord")) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Core.messagesConfig.getString("discord")));
            return true;
        }

        return false;
    }
}
