package xyz.fxcilities.core.actionbar;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import xyz.fxcilities.core.logging.Chat;

public class PlayerActionBar {
    private final Player player;
    private String content;

    public PlayerActionBar(Player player) {
        this.player = player;
    }

    public void setBar(String content) {
        this.content = content;
    }

    public void sendBar() {
        getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                Chat.format(content)
        ));
    }

    public Player getPlayer() {
        return this.player;
    }
}
