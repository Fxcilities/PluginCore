package xyz.fxcilities.core.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import xyz.fxcilities.core.Core;

public class PAPIExpansion extends PlaceholderExpansion {
    private final ExtensionHandler manager;

    public PAPIExpansion(ExtensionHandler manager) {
        this.manager = manager;
    }

    @Override
    public String getIdentifier() {
        return Core.getInstance().getPluginName();
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
    public String onRequest(OfflinePlayer player, String params) {
        return manager.onRequest(player, params);
    }
}
