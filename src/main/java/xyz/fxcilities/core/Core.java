package xyz.fxcilities.core;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.fxcilities.core.command.ServerCommand;
import xyz.fxcilities.core.logging.CustomLogger;
import xyz.fxcilities.core.placeholders.PAPIExpansion;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The base class of this project To create a plugin, instead of extending {@link JavaPlugin},
 * extend this and import all abstract classes
 *
 * <p>Example:
 *
 * <pre>{@code
 * public final class MyPlugin extends Core {
 *
 *   @Override
 *   public void onPluginEnable() {
 *     console.print("Hello world!");
 *
 *     // Initialize commands
 *     new MyCommand();
 *   }
 *
 *   @Override
 *   public void onPluginDisable() {
 *     console.print("Goodbye world!");
 *   }
 *
 *   @Override
 *   public String getPrefix() {
 *     return "&bMyPlugin > &f";
 *   }
 *
 *   @Override
 *   public String getPluginVersion() {
 *     return "v1.0";
 *   }
 *
 *   @Override
 *   public String getPluginName() {
 *     return "MyPlugin";
 *   }
 *
 *   @Override
 *   public String[] getPluginAuthors() {
 *     return new String[]{"Mario", "Luigi"};
 *   }
 * }
 * }</pre>
 *
 * Source at: https://github.com/Fxcilities/PluginCore
 */
public abstract class Core extends JavaPlugin implements Global {

    public static CustomLogger console;
    public static Core instance;

    public String notAPlayerMessage = "{PREFIX}&c&lYou must be a player to run this command!";
    public String onCooldownMessage =
            "{PREFIX}&cYou are on a cooldown! You may run this command again in &l{TIME}";

    public ArrayList<ServerCommand> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        console = new CustomLogger(this);
        instance = this;

        onPluginEnable();
        CommandMap commandMap;

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        for (ServerCommand command : commands) {
            commandMap.register(command.getLabel(), command);
            console.print(true, "Registered command /" + command.getLabel());
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            console.print(true, "Found PlaceholderAPI, registering placeholders");
            for (PAPIExpansion expansion : PAPIExpansion.expansions) {
                expansion.register();
            }
        }
    }

    @Override
    public void onDisable() {
        onPluginDisable();
    }

    /** A function called when the plugin is enabled */
    public abstract void onPluginEnable();
    /** A function called when the plugin is disabled */
    public abstract void onPluginDisable();

    /**
     * @return The prefix of the plugin
     */
    public abstract String getPrefix();

    /**
     * @return The version of the plugin
     */
    public abstract String getPluginVersion();

    /**
     * @return The name of the plugin
     */
    public abstract String getPluginName();

    /**
     * @return An array of authors of the plugin
     */
    public abstract String[] getPluginAuthors();

    /**
     * Sets the message for when a command is playerOnly and ran by console or command block.
     *
     * @param message The new message. {PREFIX} will be replaced with the prefix of the plugin.
     * @see #getPrefix()
     */
    public void setNotAPlayerMessage(String message) {
        this.notAPlayerMessage = message;
    }

    /**
     * Sets the message for when a user is on cooldown.
     *
     * @param message The new message. {PREFIX} will be replaced with the prefix of the plugin.
     *     {TIME} will be replaced with the time left on the cooldown in the cooldown duration
     *     TimeUnit.
     * @see #getPrefix()
     * @see ServerCommand#setCooldownDuration(long, TimeUnit)
     */
    public void setOnCooldownMessage(String message) {
        this.onCooldownMessage = message;
    }

    /**
     * Loads a yml configuration file from the plugin's data folder.
     *
     * @param fileName The name of the file. Example: "config.yml"
     * @return The loaded configuration file
     */
    public FileConfiguration loadConfig(String fileName) {
        Checks.nonNull(fileName, "The fileName argument");

        FileConfiguration config =
                YamlConfiguration.loadConfiguration(new File(getDataFolder(), fileName));
        saveResource(fileName, false);
        InputStream stream = getResource(fileName);

        Checks.check(stream == null, "Failed to open a InputStream from the argument fileName");

        config.setDefaults(
                YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
        config.options().copyDefaults(true);

        return config;
    }

    /**
     * @return The instance of the plugin
     */
    public static Core getInstance() {
        return instance;
    }
}
