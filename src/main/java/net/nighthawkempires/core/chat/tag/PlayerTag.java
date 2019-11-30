package net.nighthawkempires.core.chat.tag;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public abstract class PlayerTag {

    public abstract String getName();

    public TextComponent getFor(Player player) {
        return new TextComponent(TextComponent.fromLegacyText("TAG"));
    }

    public abstract int getPriority();
}
