package com.github.valkyrienyanko.core.events;

import com.github.valkyrienyanko.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Events implements Listener {
    @EventHandler
    private void CreatureSpawnEvent(CreatureSpawnEvent e) {
        if (Core.mainConfig.getBoolean("disable_mob_spawning")) {
            e.setCancelled(true);
        }
    }
}
