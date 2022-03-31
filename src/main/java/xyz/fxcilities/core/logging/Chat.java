package xyz.fxcilities.core.logging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.fxcilities.core.Core;

/** A utility class for sending chat messages to players */
public class Chat {

    /**
     * Formats a message
     *
     * @param text String to format with chat colour codes (Replaces {@literal &} with the special
     *     character)
     * @return The formatted string
     */
    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Send a messagge to a {@link CommandSender}. Will automatically format using {@link
     * #format(String)}
     *
     * @param showPrefix If the prefix should be sent in the message
     * @param sender Sender to send the message to. {@link Player} can be passed in, and everything
     *     else that {@link CommandSender} (in this case {@link
     *     org.bukkit.permissions.ServerOperator})
     * @param text String of text to send
     */
    public static void say(boolean showPrefix, CommandSender sender, String text) {
        StringBuilder content = new StringBuilder();
        if (showPrefix) {
            content.append(Core.getInstance().getPrefix());
        }
        content.append(text);
        sender.sendMessage(format(content.toString()));
    }

    /**
     * Sends a message to everyone online with a certainty permission
     *
     * @param permission The permission string to filter out
     * @param showPrefix If the prefix should be sent in the message
     * @param text Message to be sent.
     */
    public static void say(String permission, boolean showPrefix, String text) {
        StringBuilder content = new StringBuilder();
        if (showPrefix) {
            content.append(Core.getInstance().getPrefix());
        }
        content.append(text);

        String newText = format(content.toString());

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessage(newText);
            }
        }
    }

    /**
     * Sends a message to everyone online with a certainty permission
     *
     * @param permission The permission string to filter out
     * @param text Message to be sent.
     */
    public static void say(String permission, String text) {
        say(permission, true, text);
    }
}
