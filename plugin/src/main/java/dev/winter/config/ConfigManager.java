package dev.winter.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration config;
    private File languageFile;
    private FileConfiguration language;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        languageFile = new File(plugin.getDataFolder(), "language.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        if (!languageFile.exists()) {
            plugin.saveResource("language.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        language = YamlConfiguration.loadConfiguration(languageFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getLanguage() {
        return language;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
            language.save(languageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        languageFile = new File(plugin.getDataFolder(), "language.yml");
        language = YamlConfiguration.loadConfiguration(languageFile);
    }

}