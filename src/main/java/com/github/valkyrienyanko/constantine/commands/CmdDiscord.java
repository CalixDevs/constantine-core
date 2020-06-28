package com.github.valkyrienyanko.constantine.commands;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdDiscord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("discord")) {
            if (!Root.mainConfig.getBoolean("discord_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("command_disabled")));
                return true;
            }

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("discord")));
            return true;
        }

        return false;
    }
}
