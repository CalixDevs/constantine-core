package com.github.valkyrienyanko.constantine.commands;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("help")) {
            if (!Root.mainConfig.getBoolean("help_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("command_disabled")));
                return true;
            }

            for (String line : Root.messagesConfig.getStringList("help"))
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

            return true;
        }

        return false;
    }
}
