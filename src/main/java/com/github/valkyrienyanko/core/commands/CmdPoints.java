package com.github.valkyrienyanko.core.commands;

import com.github.valkyrienyanko.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CmdPoints implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("points")) {
            for (String line : (List<String>) Core.messagesConfig.getList("points"))
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

            return true;
        }

        return false;
    }
}

