package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.user.UserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.nighthawkempires.core.CorePlugin.*;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = getUserRegistry().getUser(player.getUniqueId());

        event.getRecipients().clear();
        event.setCancelled(true);

        if (userModel.isMuted()) {
            player.sendMessage(userModel.getActiveMute().getMuteInfo());
            return;
        }

        if (getChatFormat().isCancelled(event.getMessage())) {
            getChatFormat().clear(event.getMessage());
            return;
        }

        getChatFormat().sendMessage(getChatFormat().getFormattedMessage(player, event.getMessage()));
    }
}