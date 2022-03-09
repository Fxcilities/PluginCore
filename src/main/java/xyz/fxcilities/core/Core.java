package xyz.fxcilities.core;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.fxcilities.core.command.ServerCommand;
import xyz.fxcilities.core.logging.CustomLogger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * TODO: Implement https://github.com/thekeenant/tabbed
 */
public abstract class Core extends JavaPlugin implements Global {

    public static CustomLogger console;
    public static Core instance;

    public ArrayList<ServerCommand> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        console = new CustomLogger(this, getLogger());
        instance = this;

        onPluginEnable();

        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (ServerCommand command : commands) {
            commandMap.register(command.getLabel(), command);
        }

        console.print(true, getPluginName() + " &fis running &c" + VERSION);
    }

    @Override
    public void onDisable() {
        onPluginDisable();
    }

    public abstract String getPrefix();
    public abstract String getPluginName();
    public abstract String[] getPluginAuthors();

    public abstract void onPluginDisable();
    public abstract void onPluginEnable();

    public FileConfiguration loadConfig(String fileName) {
        InputStream stream = getResource(fileName);

        Checks.nonNull(fileName, "The fileName argument");
        Checks.check(stream == null, "Failed to open a InputStream from the argument fileName");

        FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8));
        config.options().copyDefaults(true);
        return config;
    }

    public static Core getInstance() {
        return instance;
    }

}
