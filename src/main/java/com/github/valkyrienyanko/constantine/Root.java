package com.github.valkyrienyanko.constantine;

import com.github.valkyrienyanko.constantine.commands.*;
import com.github.valkyrienyanko.constantine.configs.ConfigManager;
import com.github.valkyrienyanko.constantine.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Root extends JavaPlugin {
    public static File pluginFolder;

    public static ConfigManager mainCM;
    public static YamlConfiguration mainConfig;

    public static ConfigManager messagesCM;
    public static YamlConfiguration messagesConfig;

    public static boolean broadcast = false;
    public static int broadcastId;
    public static int broadcastCounter = 0;

    public static Root INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        pluginFolder = getDataFolder();

        initConfigs();
        initEvents();
        initCommands();

        setAlwaysDay();
        broadcastMessages();
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

        defaultSet(messagesConfig, "command_disabled", "&cThis command is currently disabled!");
        defaultSet(messagesConfig, "no_permission", "&cNo permission.");
        defaultSet(messagesConfig, "reload_success", "&aRoot has successfully reloaded config.yml & messages.yml");
        defaultSet(messagesConfig, "reload_fail", "&cAn error occured...");

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

        List<String> broadcast1 = new ArrayList<>();
        broadcast1.add("&aDo you like our server?");
        broadcast1.add("&bYou'd better :)");
        defaultSet(messagesConfig, "autobroadcast_1", broadcast1);

        List<String> broadcast2 = new ArrayList<>();
        broadcast2.add("&chi!");
        broadcast2.add("&di like train");
        defaultSet(messagesConfig, "autobroadcast_2", broadcast2);

        messagesCM.saveConfig();
    }

    private void initMainConfig() {
        defaultSet(mainConfig, "always_day", true);
        defaultSet(mainConfig, "no_weather", true);
        defaultSet(mainConfig, "disable_mob_spawning", true);

        defaultSet(mainConfig, "autobroadcast", true);
        defaultSet(mainConfig, "autobroadcast_sound", true);
        defaultSet(mainConfig, "autobroadcast_interval", 300);
        defaultSet(mainConfig, "autobroadcast_amount", 2);

        defaultSet(mainConfig, "discord_command", true);
        defaultSet(mainConfig, "store_command", true);
        defaultSet(mainConfig, "website_command", true);
        defaultSet(mainConfig, "teamspeak_command", true);
        defaultSet(mainConfig, "points_command", true);
        defaultSet(mainConfig, "help_command", true);

        List<String> disabled = new ArrayList<>();
        disabled.add("ip");
        disabled.add("pl");
        disabled.add("add_more_commands_here");
        defaultSet(mainConfig, "disabled_commands", disabled);

        mainCM.saveConfig();
    }

    private void initEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
    }

    private void initCommands() {
        getCommand("discord").setExecutor(new CmdDiscord());
        getCommand("store").setExecutor(new CmdStore());
        getCommand("teamspeak").setExecutor(new CmdTeamspeak());
        getCommand("website").setExecutor(new CmdWebsite());
        getCommand("help").setExecutor(new CmdHelp());
        getCommand("points").setExecutor(new CmdPoints());
        getCommand("root").setExecutor(new CmdRoot());
    }

    public void broadcastMessages() {
        if (!mainConfig.getBoolean("autobroadcast"))
            return;

        broadcast = true;

        final boolean sound = mainConfig.getBoolean("autobroadcast_sound");
        final long interval = mainConfig.getLong("autobroadcast_interval");
        final int amount = mainConfig.getInt("autobroadcast_amount");

        broadcastId = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            List<String> message = getBroadcastMessage(broadcastCounter);
            getLogger().info("Sending broadcast #" + (broadcastCounter + 1) + " to " + Bukkit.matchPlayer("").size() + " players.");

            for (Player player : Bukkit.matchPlayer("") /* Workaround */) {
                for (String line : message)
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));

                if (sound)
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            }

            broadcastCounter = (broadcastCounter + 1) % amount;
        }, 0L, interval * 20L);
    }

    private List<String> getBroadcastMessage(int k) {
        return messagesConfig.getStringList("autobroadcast_" + (k + 1));
    }

    private void setAlwaysDay() {
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
