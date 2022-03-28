package xyz.fxcilities.core.placeholders.handlers;

import org.bukkit.entity.Player;

/**
 * @see xyz.fxcilities.core.placeholders.PAPIExpansion
 */
public interface ExtensionHandler {
    String onRequest(Player player, String placeholder);
    String getPrefix();
}
