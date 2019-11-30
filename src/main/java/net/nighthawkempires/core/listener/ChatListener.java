package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.user.UserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = CorePlugin.getUserRegistry().getUser(player.getUniqueId());

        event.getRecipients().clear();
        event.setCancelled(true);

        if (userModel.isMuted())return;

        CorePlugin.getChatFormat().sendMessage(CorePlugin.getChatFormat().getFormattedMessage(player, event.getMessage()));
    }
}
