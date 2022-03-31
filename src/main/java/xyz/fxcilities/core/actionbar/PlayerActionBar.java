package xyz.fxcilities.core.actionbar;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import xyz.fxcilities.core.logging.Chat;

/** Represents an action bar for the player */
public class PlayerActionBar {
  private final Player player;
  private String content;

  public PlayerActionBar(Player player) {
    this.player = player;
  }

  /**
   * Set the action bars content
   *
   * @param content The new content of the action bar
   */
  public void setBar(String content) {
    this.content = content;
  }

  /** Send the action bar */
  public void sendBar() {
    getPlayer()
        .spigot()
        .sendMessage(
            ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Chat.format(content)));
  }

  /**
   * @return The player that the action bar belongs to.
   */
  public Player getPlayer() {
    return this.player;
  }
}
