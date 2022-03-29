package xyz.fxcilities.core.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import xyz.fxcilities.core.Core;
import xyz.fxcilities.core.placeholders.handlers.ExtensionHandler;

/**
 * Manages a PlaceholderAPI extension
 * Example:
 * <pre>
 * {@code
 * new PAPIExpansion(new ExtensionHandler() {
 *
 *     @Override
 *     public String getPrefix() {
 *         return "example";
 *     }
 *
 *     @Override
 *     public String onRequest(Player player, String placeholder) {
 *         // Registers two placeholders (%example_uuid%, and %example_name%)
 *         String result = null;
 *         switch (placeholder) {
 *             case "uuid":
 *                 result = player.getUniqueId().toString();
 *                 break;
 *             case "name":
 *                 result = player.getName();
 *                 break;
 *         }
 *         return result;
 *     }
 * });
 * }
 * </pre>
 * @see PlaceholderExpansion
 */
public class PAPIExpansion extends PlaceholderExpansion {
    private final ExtensionHandler manager;

    /**
     * Register and initialize PAPI extension
     * @param manager The extension handler. Can be created with a lambda or {@code new ExtensionHandler() { ... }}
     */
    public PAPIExpansion(ExtensionHandler manager) {
        this.manager = manager;
    }

    @Override
    public String getIdentifier() {
        return manager.getPrefix();
    }

    @Override
    public String getAuthor() {
        return Core.getInstance().getPluginAuthors()[0];
    }

    @Override
    public String getVersion() {
        return Core.getInstance().getPluginVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        return manager.onRequest(player, params);
    }
}
