package com.github.valkyrienyanko.core;

import com.github.valkyrienyanko.core.commands.CmdDiscord;
import com.github.valkyrienyanko.core.commands.CmdStore;
import com.github.valkyrienyanko.core.commands.CmdTeamspeak;
import com.github.valkyrienyanko.core.commands.CmdWebsite;
import com.github.valkyrienyanko.core.configs.ConfigManager;
import com.github.valkyrienyanko.core.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public class Core extends JavaPlugin {
    public static File pluginFolder;

    public static ConfigManager mainCM;
    public static YamlConfiguration mainConfig;

    public static ConfigManager messagesCM;
    public static YamlConfiguration messagesConfig;

    @Override
    public void onEnable() {
        pluginFolder = getDataFolder();

        initConfigs();
        initEvents();
        initCommands();

        setAlwaysDay();
    }

    private void initConfigs() {
        mainCM = new ConfigManager("config");
        mainConfig = mainCM.getConfig();
        mainConfig.options().copyDefaults(true);

        messagesCM = new ConfigManager("messages");
        messagesConfig = messagesCM.getConfig();
        messagesConfig.options().copyDefaults(true);

        initMainConfig();
        initMessagesConfig();
    }

    private void initMessagesConfig() {
        defaultSet(messagesConfig, "discord", "&d&ndiscord.zorapvp.net");
        defaultSet(messagesConfig, "store", "&d&nstore.zorapvp.net");
        defaultSet(messagesConfig, "website", "&d&nzorapvp.net");
        defaultSet(messagesConfig, "teamspeak", "&d&nts.zorapvp.net");
        messagesCM.saveConfig();
    }

    private void initMainConfig() {
        defaultSet(mainConfig, "always_day", true);
        defaultSet(mainConfig, "no_weather", true);
        defaultSet(mainConfig, "disable_mob_spawning", true);
        defaultSet(mainConfig, "discord_command", true);
        defaultSet(mainConfig, "store_command", true);
        defaultSet(mainConfig, "website_command", true);
        defaultSet(mainConfig, "teamspeak_command", true);
        mainCM.saveConfig();
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
    }

    private void initCommands() {
        if (mainConfig.getBoolean("discord_command")) {
            getCommand("discord").setExecutor(new CmdDiscord());
        }

        if (mainConfig.getBoolean("discord_command")) {
            getCommand("store").setExecutor(new CmdStore());
        }

        if (mainConfig.getBoolean("discord_command")) {
            getCommand("teamspeak").setExecutor(new CmdTeamspeak());
        }

        if (mainConfig.getBoolean("discord_command")) {
            getCommand("website").setExecutor(new CmdWebsite());
        }
    }

    public void setAlwaysDay() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    if (mainConfig.getBoolean("always_day")) {
                        world.setTime(5000);
                    }

                    if (mainConfig.getBoolean("no_weather")) {
                        world.setStorm(false);
                        world.setThundering(false);
                    }
                }
            }
        }, 0L, 1L);
    }

    private void defaultSet(YamlConfiguration config, String path, Object value) {
        if (!config.isSet(path)) {
            config.set(path, value);
        }
    }
}
