package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ConsoleTag extends PlayerTag {

    public String getName() {
        return "console";
    }

    public TextComponent getComponentFor(Player player) {
        TextComponent tag = new TextComponent("[");
        tag.setColor(ChatColor.DARK_GRAY);
        TextComponent console = new TextComponent("Console");
        console.setColor(ChatColor.GOLD);
        TextComponent star = new TextComponent("*");
        star.setColor(ChatColor.DARK_GRAY);
        star.setBold(true);
        star.setItalic(true);
        TextComponent hawkeye = new TextComponent("Hawkeye");
        hawkeye.setColor(ChatColor.GRAY);
        hawkeye.setBold(true);
        hawkeye.setItalic(true);
        tag.addExtra(console);
        tag.addExtra("]");
        tag.addExtra(star);
        tag.addExtra(hawkeye);
        tag.addExtra(star);
        return tag;
    }

    public int getPriority() {
        return 2;
    }
}
