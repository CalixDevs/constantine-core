package com.github.valkyrienyanko.constantine.events;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class Events implements Listener {
    @EventHandler
    private void creatureSpawnEvent(CreatureSpawnEvent e) {
        if (Root.mainConfig.getBoolean("disable_mob_spawning")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void command(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage().split(" ")[0];
        List<String> disabled = Root.mainConfig.getStringList("disabled_commands");

        for (String d : disabled) {
            if (("/" + d).equalsIgnoreCase(command)) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Root.messagesConfig.getString("command_disabled")));

                e.setCancelled(true);
                return;
            }
        }
    }
}
