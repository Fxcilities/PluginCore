package xyz.fxcilities.core.logging;

import xyz.fxcilities.core.Core;

import java.util.logging.Logger;

public class CustomLogger {
    private BukkitLoggerOverride logger;
    private Core plugin;

    public CustomLogger(Core plugin, Logger logger) {
        this.logger = (BukkitLoggerOverride) logger;
        this.plugin = plugin;
    }

    /* Logging Methods */
    public void print(boolean prefix, String text) {
        StringBuilder logRecord = new StringBuilder();
        if (prefix) {
            logRecord.append(plugin.getPrefix());
        }
        logRecord.append(text);
        logger.info(Chat.format(logRecord.toString()));
    }

    public void print(String text) {
        print(false, text);
    }

    public Core getPlugin() {
        return this.plugin;
    }
}
