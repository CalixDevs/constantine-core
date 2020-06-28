package com.github.valkyrienyanko.core;

import com.github.valkyrienyanko.core.commands.*;
import com.github.valkyrienyanko.core.configs.ConfigManager;
import com.github.valkyrienyanko.core.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        List<String> points = new ArrayList<>();
        points.add("&5&m------------------------------------");
        points.add("&dFaction Top Point System");
        points.add("");
        points.add("&fPer kill: &d1 Point");
        points.add("&fKoth capture: &d5 Points");
        points.add("&5&m------------------------------------");
        defaultSet(messagesConfig, "points", points);

        List<String> help = new ArrayList<>();
        help.add("&5&m------------------------------------");
        help.add("&dZoraPvP Help command");
        help.add("");
        help.add("&f/discord: &dZoraPvP's discord server invite.");
        help.add("&f/teamspeak: &dZoraPvP's teamspeak IP.");
        help.add("&f/website: &dZoraPvP's website url.");
        help.add("&f/store: &dZoraPvP's store url.");
        help.add("&5&m------------------------------------");
        defaultSet(messagesConfig, "help", help);

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

        defaultSet(mainConfig, "points_command", true);
        defaultSet(mainConfig, "help_command", true);
        mainCM.saveConfig();
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
    }

    private void initCommands() {
        if (mainConfig.getBoolean("discord_command"))
            getCommand("discord").setExecutor(new CmdDiscord());

        if (mainConfig.getBoolean("discord_command"))
            getCommand("store").setExecutor(new CmdStore());

        if (mainConfig.getBoolean("discord_command"))
            getCommand("teamspeak").setExecutor(new CmdTeamspeak());

        if (mainConfig.getBoolean("discord_command"))
            getCommand("website").setExecutor(new CmdWebsite());

        if (mainConfig.getBoolean("help_command"))
            getCommand("help").setExecutor(new CmdHelp());

        if (mainConfig.getBoolean("points_command"))
            getCommand("points").setExecutor(new CmdPoints());
    }

    public void setAlwaysDay() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for (World world : Bukkit.getWorlds()) {
                if (mainConfig.getBoolean("always_day"))
                    world.setTime(5000);

                if (mainConfig.getBoolean("no_weather")) {
                    world.setStorm(false);
                    world.setThundering(false);
                }
            }
        }, 0L, 1L);
    }

    private void defaultSet(YamlConfiguration config, String path, Object value) {
        if (!config.isSet(path))
            config.set(path, value);
    }
}
