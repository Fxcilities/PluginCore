package xyz.fxcilities.core.placeholders.handlers;

import org.bukkit.entity.Player;

/**
 * @see xyz.fxcilities.core.placeholders.PAPIExpansion
 */
public interface ExtensionHandler {
    /**
     * Called when a placeholder request is made
     *
     * @param player The player that requested the placeholder
     * @param placeholder The placeholder that was requested, prefix not includes
     * @return The result of the request
     */
    String onRequest(Player player, String placeholder);

    /**
     * @return The prefix of the extension
     */
    String getPrefix();
}
