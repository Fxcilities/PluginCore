package xyz.fxcilities.core.placeholders;

import org.bukkit.OfflinePlayer;

public interface PlaceholderManager {
    String onRequest(OfflinePlayer player, String placeholder);
}
