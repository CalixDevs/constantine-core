package com.github.valkyrienyanko.constantine.commands;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdPoints implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("points")) {
            if (!Root.mainConfig.getBoolean("points_command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("command_disabled")));
                return true;
            }

            for (String line : Root.messagesConfig.getStringList("points"))
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

            return true;
        }

        return false;
    }
}

