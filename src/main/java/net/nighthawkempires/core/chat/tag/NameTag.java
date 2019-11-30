package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class NameTag extends PlayerTag {

    public String getName() {
        return "name";
    }

    public int getPriority() {
        return 999;
    }

    public TextComponent getFor(Player player) {
        TextComponent tag = new TextComponent("");

        for (BaseComponent baseComponent : TextComponent.fromLegacyText(" " + player.getDisplayName()))
            tag.addExtra(baseComponent);

        return tag;
    }
}
