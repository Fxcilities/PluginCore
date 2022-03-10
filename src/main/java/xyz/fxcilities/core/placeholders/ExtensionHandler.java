package xyz.fxcilities.core.placeholders;

import org.bukkit.OfflinePlayer;

public interface ExtensionHandler {
    String onRequest(OfflinePlayer player, String placeholder);
}
