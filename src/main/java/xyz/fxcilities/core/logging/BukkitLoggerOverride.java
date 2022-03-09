package xyz.fxcilities.core.logging;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// Modifying bukkits plugin logger
public class BukkitLoggerOverride extends Logger {

    /**
     * Creates a new {@link BukkitLoggerOverride} that extracts the name from a plugin.
     * Removes the prefix from the default {@link org.bukkit.plugin.PluginLogger}
     * @param context A reference to the plugin
     */

    public BukkitLoggerOverride(Plugin context) {
        super(context.getClass().getCanonicalName(), null);
        setParent(context.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(logRecord.getMessage());
        super.log(logRecord);
    }

}
