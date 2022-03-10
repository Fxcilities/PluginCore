package xyz.fxcilities.core.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.fxcilities.core.Core;

public class PAPIExpansion extends PlaceholderExpansion {
    private final ExtensionHandler manager;

    public PAPIExpansion(ExtensionHandler manager) {
        this.manager = manager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return Core.getInstance().getPluginName();
    }

    @Override
    public @NotNull String getAuthor() {
        return Core.getInstance().getPluginAuthors()[0];
    }

    @Override
    public @NotNull String getVersion() {
        return Core.getInstance().getPluginVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return manager.onRequest(player, params);
    }
}
