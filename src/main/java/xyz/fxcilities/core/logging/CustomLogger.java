package xyz.fxcilities.core.logging;

import xyz.fxcilities.core.Core;

/**
 * A custom logger
 *
 * @see Core#console
 */
public class CustomLogger {
    private BukkitLoggerOverride logger;
    private Core plugin;

    /**
     * Creates a custom console logger
     *
     * @param plugin The core plugin
     * @see BukkitLoggerOverride
     */
    public CustomLogger(Core plugin) {
        this.logger = new BukkitLoggerOverride(plugin);
        this.plugin = plugin;
    }

    /**
     * Prints a message to console
     *
     * @param prefix If the prefix should be sent before the text
     * @param text The text to log
     */
    public void print(boolean prefix, String text) {
        StringBuilder logRecord = new StringBuilder();
        if (prefix) {
            logRecord.append(plugin.getPrefix());
        }
        logRecord.append(text);
        logger.info(Chat.format(logRecord.toString()));
    }

    /**
     * Prints a message to console
     *
     * @param text The text to log
     */
    public void print(String text) {
        print(false, text);
    }

    /**
     * @return The Core plugin the logger belongs to
     */
    public Core getPlugin() {
        return this.plugin;
    }
}
