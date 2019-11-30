package net.nighthawkempires.core.chat.format;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.nighthawkempires.core.chat.tag.ConsoleTag;
import net.nighthawkempires.core.chat.tag.PlayerTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class ChatFormat {

    private List<PlayerTag> playerTags;

    private ConcurrentMap<String, Boolean> cancelMessage;

    public ChatFormat() {
        playerTags = Lists.newArrayList();
        cancelMessage = Maps.newConcurrentMap();
    }

    public ChatFormat add(PlayerTag playerTag) {
        playerTags.add(playerTag);

        // SHOULD JUST BE THIS EASY!
        playerTags.sort(Comparator.comparing(PlayerTag::getPriority));
        return this;
    }

    public ChatFormat addAll(Collection<PlayerTag> playerTags) {
        playerTags.forEach(this::add);
        return this;
    }

    public ChatFormat addAll(PlayerTag[] playerTags) {
        Arrays.asList(playerTags).forEach(this::add);
        return this;
    }

    public ImmutableList<PlayerTag> getPlayerTags() {
        return ImmutableList.copyOf(playerTags);
    }

    public TextComponent getTags(TextComponent parent, Player player) {
        for (PlayerTag playerTag : playerTags) {
            BaseComponent[] space = TextComponent.fromLegacyText(" ");
            TextComponent component = playerTag.getFor(player);
            if (component != null) {
                parent.addExtra(component.duplicate());
            }
        }
        return parent;
    }

    public BaseComponent getFormattedMessage(Player player, String message) {
        TextComponent component = new TextComponent("");
        component = getTags(component, player);
        component.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        TextComponent next = new TextComponent("» ");
        next.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        component.addExtra(next);
        String finalMessage = ChatColor.WHITE + message;
        if (player.hasPermission("ne.chat.color")) {
            finalMessage = ChatColor.translateAlternateColorCodes('&', finalMessage);
        }
        for (BaseComponent components : TextComponent.fromLegacyText(finalMessage)) {
            component.addExtra(components);
        }
        return component;
    }

    public BaseComponent getFormattedMessage(ConsoleCommandSender console, String message) {
        TextComponent component = new TextComponent("");
        component.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        TextComponent next = new TextComponent("» ");
        next.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        component.addExtra(new ConsoleTag().getComponentFor(null));
        component.addExtra(next);
        String finalMessage = ChatColor.WHITE + message;
        if (console.hasPermission("ne.chat.color")) {
            finalMessage = ChatColor.translateAlternateColorCodes('&', finalMessage);
        }
        for (BaseComponent components : TextComponent.fromLegacyText(finalMessage)) {
            component.addExtra(components);
        }
        return component;
    }

    public void sendMessage(BaseComponent baseComponent) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(baseComponent);
        }
        Bukkit.getConsoleSender().spigot().sendMessage(baseComponent);
    }

    public void setCancelled(String message, boolean cancelled) {
        this.cancelMessage.put(message, cancelled);
    }

    public boolean isCancelled(String message) {
        return this.cancelMessage.containsKey(message) && this.cancelMessage.get(message);
    }

    public void clear(String message) {
        this.cancelMessage.remove(message);
    }
}
