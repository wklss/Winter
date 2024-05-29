package dev.winter;

import dev.winter.commands.chat.StaffChatCommand;
import dev.winter.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Winter extends JavaPlugin {
    private ConfigManager configManager;
    private static Winter instance;

    @Override
    public void onEnable() {
        // ConfigManager startup logic
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        // Register Commands & Register Events
        registerCommands();
        registerEvents();

        // Instace
        instance = this;
    }

    @Override
    public void onDisable() {
        // ConfigManager shutdown logic
        configManager.saveConfig();
    }

    public ConfigManager getManager() {
        return configManager;
    }

    public static Winter getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("staffchat").setExecutor(new StaffChatCommand());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new StaffChatCommand(), this);
    }
}