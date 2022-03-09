package xyz.fxcilities.core.logging;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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
}
