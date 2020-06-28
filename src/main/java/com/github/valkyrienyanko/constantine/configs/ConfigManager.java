package com.github.valkyrienyanko.constantine.configs;

import java.io.File;
import java.io.IOException;

import com.github.valkyrienyanko.constantine.Root;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    private File file;
    private YamlConfiguration config;

    public ConfigManager(String name) {
        file = new File(Root.pluginFolder, name + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}