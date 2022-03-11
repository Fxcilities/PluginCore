package xyz.fxcilities.core.logging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.fxcilities.core.Core;

public class Chat {

    public static String format(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void say(boolean showPrefix, CommandSender sender, String text) {
        StringBuilder content = new StringBuilder();
        if (showPrefix) {
            content.append(Core.getInstance().getPrefix());
        }
        content.append(text);
        sender.sendMessage(format(content.toString()));
    }

    public static void say(String permission, String text) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessage(format(text));
            }
        }
    }
}
